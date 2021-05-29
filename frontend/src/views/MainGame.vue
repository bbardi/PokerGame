<template>
    <v-container class="fill-height">
        <v-row>
            <v-col cols="6">
                <v-virtual-scroll :items="chatlog" height="175" item-height="20">
                    <template v-slot:default="{ item }">
                        <v-list-item :key="item">
                            <v-list-item-content class="text-left align-self-start">
                                <div><b>{{item.username}}:</b> {{item.message}}</div>
                            </v-list-item-content>
                        </v-list-item>
                    </template>
                </v-virtual-scroll>
                <v-text-field
                    label="Message"
                    v-model=currentMessage
                />
                <v-btn @click="sendMessage">Send</v-btn>
            </v-col>
            <v-col cols="6">
                <v-btn @click="sendReady" :disabled="disableReady">I am READY</v-btn>
                <div v-if="currentHand">
                    <div v-if="currentTurn"> 
                    <h3>You currently have: {{turnData.currentAmount}}USD</h3>
                    <h3>You have betted: {{turnData.betAmount}}USD</h3>
                    <h3>Bet required: {{turnData.betRequired}}USD</h3>
                    <h3>Bet limit: {{turnData.betLimit}}USD</h3>
                    </div>
                    <h3>Your Hand is:</h3>
                    <h1>{{currentHand[0]}}{{currentHand[1]}}</h1>
                    <v-btn :disabled="!currentTurn" @click="fold">Fold</v-btn>
                    <v-btn :disabled="!currentTurn" @click="call">Call</v-btn>
                    <v-btn :disabled="!currentTurn" @click="check">Check</v-btn>
                    <v-btn :disabled="!currentTurn" @click="raise">Raise</v-btn>
                    <v-text-field label="Amount" v-model="amount" :disabled="!currentTurn"/>
                    <h3> The board currently has: </h3>
                    <h1 v-for="card in revealedCards" :key="card.id">
                        {{card}}
                    </h1>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import api from "../api";

export default {
    props:{
        lobbyID: null
    },
    data: () => ({
        hello: "hello",
        chatlog: [],
        disableReady: false,
        currentTurn: false,
        turnData: {},
        currentHand: null,
        revealedCards: [],
        currentMessage: "",
        connected: false,
        amount: ""
    }),
    methods:{
        raise(){
            var username = this.$store.getters["auth/getUsername"];
            var lobbyID = this.lobbyID;
            var amount = this.amount;
            this.currentTurn = false;
            this.stompClient.send("/game/raise", JSON.stringify({
                    lobbyID: lobbyID,
                    username: username,
                    amount: amount
                }),
                {})
        },
        check(){
            var username = this.$store.getters["auth/getUsername"];
            var lobbyID = this.lobbyID;
            this.currentTurn = false;
            this.stompClient.send("/game/check", JSON.stringify({
                    lobbyID: lobbyID,
                    username: username,
                }),
                {})
        },
        call(){
            var username = this.$store.getters["auth/getUsername"];
            var lobbyID = this.lobbyID;
            this.currentTurn = false;
            this.stompClient.send("/game/call", JSON.stringify({
                    lobbyID: lobbyID,
                    username: username,
                }),
                {})
        },
        fold(){
            var username = this.$store.getters["auth/getUsername"];
            var lobbyID = this.lobbyID;
            this.currentTurn = false;
            this.stompClient.send("/game/fold", JSON.stringify({
                    lobbyID: lobbyID,
                    username: username,
                }),
                {})
        },
        sendReady(){
            var username = this.$store.getters["auth/getUsername"];
            var lobbyID = this.lobbyID;
            this.stompClient.send("/game/ready", JSON.stringify({
                    lobbyID: lobbyID,
                    username: username,
                }),
                {})
        },
        sendMessage(){
            var msg = this.currentMessage;
            var username = this.$store.getters["auth/getUsername"];
            var lobbyID = this.lobbyID;
            this.stompClient.send("/chat/send",JSON.stringify({
                message: msg,
                username: username,
                lobbyID: lobbyID,
            }),
            {})
        }
    },
    created(){
        this.socket = new SockJS("http://localhost:8080/pokerws");
        this.stompClient = Stomp.over(this.socket);
        var username = this.$store.getters["auth/getUsername"];
        this.stompClient.connect(
            {},
            frame => {
                this.connected = true;
                console.log(frame);
                this.stompClient.subscribe(`/chat/${this.lobbyID}/receive`, tick => {
                    console.log(tick);
                    this.chatlog.push(JSON.parse(tick.body));
                })
                this.stompClient.subscribe(`/game/${username}`, tick =>{
                    var message = JSON.parse(tick.body);
                    console.log(message);
                    if(message.hand){
                        this.currentHand = message.hand.map(c => api.game.decodeCard(c));
                        this.revealedCards = [];
                        this.disableReady = true;
                    }else{
                        if(message.betRequired){
                            this.turnData = message;
                            this.currentTurn = true;
                        }else{
                            if(typeof message.winner !== 'undefined'){
                                if(message.winner){
                                    alert("You have won");
                                    this.$store.dispatch("auth/updateBalance",this.$store.getters["auth/getUserID"]);
                                    this.$router.push("/");
                                }
                                else{
                                    alert("You have busted");
                                    this.$store.dispatch("auth/updateBalance",this.$store.getters["auth/getUserID"]);
                                    this.$router.push("/");
                                }
                            }
                            else{
                                if(message.cardsDrawn){
                                    this.revealedCards = this.revealedCards.concat(message.cardsDrawn.map(c=> api.game.decodeCard(c)));
                                }
                            }
                        }
                    }
                })
            },
            error => {
                console.log(error);
                this.connected = false;
            }
        )
    }
}
</script>