let ACCESS_TOKEN = "";

$(document).ready(function () {

  $("#loginBtn").on("click", function () {

    $.ajax({
      type: "POST",
      url: "/api/authenticate",
      data: {
        username: $("#userId").val(),
        password: $("#password").val()
      },
      success: function (data, textStatus, request) {
        let responserHeader = request.getResponseHeader("Authorization");

        ACCESS_TOKEN = responserHeader.substring(7);

        alert("success : " + responserHeader + "")
      },
      error: function (request, status, error) {
        alert("error" + request.getResponseHeader("Authorization"))
      }
    })


  });

  $("#myInfoBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/users/myInfo",
      contentType: "application/json; charset=utf-8",
      headers: {
        "Authorization": "Bearer " + ACCESS_TOKEN
      },
      success: function (data) {
        alert(JSON.stringify(data));
      },
      error: function (data) {
        alert("error" + data);
      }
    })

  });

});
