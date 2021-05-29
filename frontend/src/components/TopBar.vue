<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-toolbar-title>Poker</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn @click="goToGame">Lobby list</v-btn>
        <v-btn @click="goToTopup">Topup</v-btn>
        <v-btn v-show="isAdmin" @click="goToUsers">Users</v-btn>
        <v-btn readonly>Balance: {{getBalance}}USD</v-btn>
      </v-toolbar-items>
      <v-spacer></v-spacer>
      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
import router from "../router";
export default {
  name: "TopBar",
  data: ()=>({
  }),
  methods: {
    goToTopup(){
      router.push("/topup");
    },
    goToGame(){
      router.push("/game");
    },
    goToUsers(){
      router.push("/users");
    },
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
  },
  computed: {
    isAdmin: function (){
      return this.$store.getters["auth/isAdmin"]
    },
    getBalance: function(){
      return this.$store.getters["auth/getBalance"];
    },
  }
};
</script>

<style scoped></style>