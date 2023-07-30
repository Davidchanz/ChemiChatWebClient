var stompClient = null;
var token = fetch("http://localhost:8081/getToken");
console.log(token);

const socket = new SockJS('http://localhost:8080/ws', {
  transportOptions: {
    'xhr-streaming': {
      headers: {
        "Authorization": `Bearer ` + token
      }
    }
  }
});
stompClient = Stomp.over(socket);