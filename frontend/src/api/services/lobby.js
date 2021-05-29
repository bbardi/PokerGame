import authHeader, { BASE_URL, HTTP } from "../http";

export default{
    createLobby(data) {
        return HTTP.post(BASE_URL + '/poker/lobby',data, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        )
    },
    joinLobby(lobbyID,userID) {
        return HTTP.post(BASE_URL + `/poker/lobby/${lobbyID}/${userID}`,null, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
                return Promise.reject();
            }
        )
    },
    getAllLobbies(){
        return HTTP.get(BASE_URL + '/poker/lobby',{headers: authHeader()}).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        )
    }
}