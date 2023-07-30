function subscribe(to){
    stompClient.connect({
      'Authorization': `Bearer ` + token
    }, function(frame) {
        console.log(frame);
        stompClient.subscribe('/specific/'+to, function(result) {
          //show(JSON.parse(result.body));
          console.log(JSON.parse(result.body));
            console.log("sub on " + to);
        });
    });
}

function show(){

}