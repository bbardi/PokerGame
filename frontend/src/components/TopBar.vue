<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-toolbar-title>Clinic</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn v-show="isAdmin" @click="goToUsers">Users</v-btn>
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
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
  },
  computed: {
    isAdmin: function (){
      return this.$store.getters["auth/isAdmin"]
    },
  }
};
</script>

<style scoped></style>