<template>
    <v-card elevation="2" >
        <v-card-title>{{cardtitle}}</v-card-title>
        <v-card-text>
            <div>
            The sum required to enter {{sumRequired}} USD
            </div>
            <div>
            Current number of players {{playerCount}} players
            </div>
        </v-card-text>
        <v-card-actions>
            <v-btn @click="onClick">
                Join Lobby
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
export default {
    name: "Lobby",
    props: ['cardtitle','sumRequired','playerCount'],
    methods:{
        onClick(){
            this.$emit('join')
        }
    }
}
</script>