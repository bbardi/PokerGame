import Vue from 'vue';
import { StripePlugin } from '@vue-stripe/vue-stripe';

const options = {
  pk: "pk_test_51Iq0JDKVmwuZuPGd1lRMUhwedRNNP5ULnNziMGjVVw5VzuzhXXyhKYvrEa0VxrTtD9p8iXe5QsInDqA2PdOjjwrm00uoB7jYtR",
};

Vue.use(StripePlugin, options);

export default {}