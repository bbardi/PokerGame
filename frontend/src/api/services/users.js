import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    fetchAll() {
        return HTTP.get(BASE_URL + "/poker/user", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    deleteUser(user) {
        return HTTP.delete(BASE_URL + `/poker/user/${user.id}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    createUser(user) {
        return HTTP.post(BASE_URL + "/poker/user", user, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },
    editUser(user) {
        return HTTP.put(BASE_URL + `/poker/user/${user.id}`, user, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            },
            (error) => {
                alert(error.response.data.message);
            }
        );
    },

    parseRoles(user) {
        return user.roles.join(",");
    }
};