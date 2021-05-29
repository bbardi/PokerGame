<template>
    <v-container fluid>
        <v-row dense>
            <v-col cols=12>
                <v-btn color="primary" dark @click="newLobby" >
                    Create Lobby
                </v-btn>
            </v-col>
            <v-col v-for="item in lobbyList" :key="item.id">
                <lobby-info
                    v-bind:cardtitle ="item.title"
                    v-bind:sumRequired ="item.minimumBalance"
                    v-bind:playerCount ="item.users.length"
                    @join="joinLobby(item.id)"
                />
            </v-col>
        </v-row>
        <LobbyDialog
            :openDialog="openDialog"
            :editedLobby="editedLobby"
            @refresh="refreshList"
        />
    </v-container>
</template>
<script>
import api from '../api'
import LobbyInfo from '../components/LobbyInfo.vue'
import LobbyDialog from '../components/LobbyDialog.vue'
import router from "../router";
export default {
    data: () => ({
        openDialog: false,
        editedLobby: {},
        lobbyList:[]
    }),
    components:{
        LobbyInfo,
        LobbyDialog
    },
    methods:{
        newLobby(){
            this.openDialog = true;
        },
        async joinLobby(id){
            var userID = this.$store.getters["auth/getUserID"];
            await api.lobby.joinLobby(id,userID).then(
                () =>
                router.push(`/maingame?id=${id}`)
            );
        },
        async refreshList(){
            this.openDialog = false;
            this.lobbyList = await api.lobby.getAllLobbies()
        }
    },
    created(){
        this.refreshList();
    }
}
</script>LobbyInfo