<template>
  <v-dialog persistent :value="openDialog" max-width="500px">
    <v-form lazy-validation v-model="valid" ref="form">
      <v-card>
        <v-card-title>
          <span class="headline">Create new Lobby</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedLobby.title"
                  label="Title"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedLobby.minimumBalance"
                  label="Minimum Balance"
                  :rules="[rules.required, rules.digits]"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
          <v-btn color="blue darken-1" text @click="save" :disabled="!valid">
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "LobbyDialog",
  props: {
    openDialog: Boolean,
    editedLobby: Object,
  },
  data: () => ({
    rules: {
      required: (value) => !!value || "Required.",
      digits: (value) => /^[1-9][0-9]*$/.test(value) || "Must be a number"
    },
    valid: true
  }),
  methods: {
    close() {
      this.$emit("refresh");
    },
    save() {
        api.lobby.createLobby(this.editedLobby).then(()=>this.$emit("refresh"));
    },
  },
};
</script>
