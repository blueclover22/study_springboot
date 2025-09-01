$(document).ready(function () {

  $("#noticeListBtn").on("click", function () {
    $.getJSON("/notices", function (list) {
      alert(JSON.stringify(list));
    })
  });

  $("#noticeReadBtn").on("click", function () {

    $.ajax({
      type: "GET",
      url: "/notices/" + $("#noticeNo").val(),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
        $("#noticeTitle").val(data.noticeTitle);
        $("#noticeContent").val(data.noticeContent);
      },
      error: function (data) {
      }
    });
  });

  $("#noticeRegisterBtn").on("click", function () {

    let noticeObject = {
      noticeTitle: $("#noticeTitle").val(),
      noticeContent: $("#noticeContent").val()
    };

    $.ajax({
      type: "POST",
      url: "/notices",
      data: JSON.stringify(noticeObject),
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
    });

  });

  $("#noticeDeleteBtn").on("click", function () {
    $.ajax({
      type: "DELETE",
      url: "/notices/" + $("#noticeNo").val(),
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

  $("#noticeModifyBtn").on("click", function () {
    let noticeNo = $("#noticeNo").val();
    let noticeObject = {
      noticeNo: noticeNo,
      noticeTitle: $("#noticeTitle").val(),
      noticeContent: $("#noticeContent").val()
    };
    $.ajax({
      type: "PUT",
      url: "/notices/" + noticeNo,
      data: JSON.stringify(noticeObject),
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

  $("#noticeResetBtn").on("click", function () {
    $("#noticeNo").val("");
    $("#noticeTitle").val("");
    $("#noticeContent").val("");
  });

})
