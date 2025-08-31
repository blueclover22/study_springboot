$(document).ready(function () {

  $("#memberListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/users",
      contentType: "application/json; charset=utf-8",
      headers: {
        "Authorization": "Bearer " + ACCESS_TOKEN
      },
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
      },
      error: function (data) {
        alert(data);
      }
    })

  });

  $("#memberReadBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/users/" + $("#userNo").val(),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
        $("#memberId").val(data.userId);
        $("#memberName").val(data.userName);
        $("#memberPw").val(data.userPw);
        $("#job").val(data.job);
        if (data.authList[0]) {
          $("#memberAuth0").val(data.authList[0].auth);
        }
        if (data.authList[1]) {
          $("#memberAuth1").val(data.authList[1].auth);
        }
        if (data.authList[2]) {
          $("#memberAuth2").val(data.authList[2].auth);
        }
      },
    })


  });

  $("#memberRegisterBtn").on("click", function () {

    let userObject = {
      userId: $("#memberId").val(),
      userPw: $("#memberPw").val(),
      userName: $("#memberName").val(),
      job: $("#job").val()
    }

    $.ajax({
      type: "POST",
      url: "/users",
      data: JSON.stringify(userObject),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert("success : " + data);
      },
      error: function (data) {
        alert("error : " + data);
      }
    })
  });

  $("#memberDeleteBtn").on("click", function () {
    $.ajax({
      type: "DELETE",
      url: "/users/" + $("#userNo").val(),
      contentType: "application/json; charset=utf-8",
      headers: {
        "Authorization": "Bearer " + ACCESS_TOKEN
      },
      dataType: "json",
      success: function (data) {
        alert("success : " + data);
      },
      error: function (data) {
        alert("error : " + data);
      }
    })


  });

  $("#memberModifyBtn").on("click", function () {

    let userNoVal = $("#userNo").val();
    let userObject = {
      userId: $("#memberId").val(),
      userPw: $("#memberPw").val(),
      userName: $("#memberName").val(),
      job: $("#job").val(),

      authList: [
        {
          userNo: userNoVal,
          auth: $("#memberAuth0").val()
        },
        {
          userNo: userNoVal,
          auth: $("#memberAuth1").val()
        },
        {
          userNo: userNoVal,
          auth: $("#memberAuth2").val()
        }
      ]
    }

    $.ajax({
      type: "PUT",
      url: "/users/" + userNoVal,
      data: JSON.stringify(userObject),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert("success : " + data);
      },
      error: function (data) {
        alert("error : " + data);
      }
    })


  });

  $("#memberResetBtn").on("click", function () {
    $("#memberId").val("");
    $("#memberPw").val("");
    $("#memberName").val("");
    $("#job").val("");
    $("#memberAuth0").val("");
    $("#memberAuth1").val("");
    $("#memberAuth2").val("");
    $("#userNo").val("");
  });

  $.getJSON("/codes/job", function (list) {
    $(list).each(function () {
      let str = "<option value='" + this.value + "'>" + this.label + "</option>";
      $("#job").append(str);
    })
  });

});
