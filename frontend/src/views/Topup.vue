<template>
  <v-container>
    <v-layout row justify-center pt-3>
      <v-flex xs5 class="grey lighten-4">
        <v-container class="text-xs-center">
          <v-card>
            <v-card-title primary-title class="justify-center">
              Top-up
            </v-card-title>
            <v-card-text>
              Select the amount you wish to add to your account
            </v-card-text>
            <v-radio-group v-model="amountSelected">
              <v-radio
                v-for="n in [5,10,25]"
                :key="n"
                :label="`${n} USD`"
                :value="n"
              >
              </v-radio>
            </v-radio-group>
            <v-card-text>
              Please enter your card details
            </v-card-text>
            <v-form id="payment-form">
              <div id="card-element"></div>
            </v-form>
            <v-btn :disabled="paymentInProcess" style="margin-top: 20px" @click="submit">Pay</v-btn>
          </v-card>
        </v-container>      
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import api from '../api'
export default {
  data: () => ({
    elements: null,
    card: null,
    amountSelected: 10,
    paymentInProcess: false,
  }),
  mounted(){
    this.elements = this.$stripe.elements();
    var style = {
      base: {
        color: "#32325d",
        fontFamily: 'Arial, sans-serif',
        fontSmoothing: "antialiased",
        fontSize: "16px",
        "::placeholder": {
          color: "#32325d"
        }
      },
      invalid: {
        fontFamily: 'Arial, sans-serif',
        color: "#fa755a",
        iconColor: "#fa755a"
      }
    };

    this.card = this.elements.create("card",{hidePostalCode: true ,style: style});
    this.card.mount("#card-element");
  },
  methods: {
    togglePayment(){
      this.paymentInProcess = !this.paymentInProcess;
    },
    async submit () {
      this.togglePayment()
      var username = this.$store.getters["auth/getUsername"];
      var intent = await api.payments.createIntent(
        {
          description: `${username} Topup`,
          currency: "USD",
          amount: this.amountSelected*100,
        }
      );

      await this.$stripe.confirmCardPayment(intent, {
        payment_method: {
          card: this.card
        }
      }).then(function(result){
        if(result.error){
          alert(result.error.message);
        }else{
          api.payments.validatePayment({
            paymentIntent: result.paymentIntent.id,
            username: username
          });
          alert("Topup successful");
        }
      })
      this.$store.dispatch("auth/updateBalance",this.$store.getters["auth/getUserID"]);
      this.togglePayment()
    },
  }
};
</script>