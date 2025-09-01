$(document).ready(function () {
  $("#adminRegisterBtn").on("click", function () {
    let userObject = {
      userId: $("#adminId").val(),
      userPw: $("#adminPw").val(),
      userName: $("#adminName").val(),
    };

    $.ajax({
      type: "POST",
      url: "/users/setup",
      data: JSON.stringify(userObject),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert("success : " + data);
      },
      error: function (xhr, status, error) {
        alert(
          "code: " +
            xhr.status +
            "\n" +
            ", message: " +
            xhr.responseText +
            "\n" +
            ", error: " +
            error
        );
      },
    });
  });

  $("#adminResetBtn").on("click", function () {
    $("#adminId").val("");
    $("#adminPw").val("");
    $("#adminName").val("");
  });
});
