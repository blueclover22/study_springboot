$(document).ready(function () {

  $("#memberListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/members",
      contentType: "application/json; charset=utf-8",
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
      url: "/members/" + $("#userNo").val(),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
        $("#userId").val(data.userId);
        $("#userName").val(data.userName);
        $("#userPw").val(data.userPw);
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
      userId: $("#userId").val(),
      userPw: $("#userPw").val(),
      userName: $("#userName").val(),
      job: $("#job").val()
    }

    $.ajax({
      type: "POST",
      url: "/members",
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
      url: "/members/" + $("#userNo").val(),
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

  $("#memberModifyBtn").on("click", function () {

    let userNoVal = $("#userNo").val();
    let userObject = {
      userId: $("#userId").val(),
      userPw: $("#userPw").val(),
      userName: $("#userName").val(),
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
      url: "/members/" + userNoVal,
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
    $("#userId").val("");
    $("#userPw").val("");
    $("#userName").val("");
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
