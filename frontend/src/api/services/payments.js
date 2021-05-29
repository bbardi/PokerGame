import authHeader, { BASE_URL, HTTP } from "../http";

export default{
    createIntent(data) {
        return HTTP.post(BASE_URL + '/poker/payments',data, { headers: authHeader() }).then(
            (response) => {
                return response.data.clientSecret;
            },
            (error) => {
                alert(error.response.data.message);
            }
        )
    },
    validatePayment(data) {
        return HTTP.put(BASE_URL + '/poker/payments',data, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        )
    },
    getBalance(userID){
        return HTTP.get(BASE_URL + `/poker/payments/${userID}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
}