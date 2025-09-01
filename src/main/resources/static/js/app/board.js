$(document).ready(function () {

  $("#boardListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/boards",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
      },
      error: function (data) {
        alert("error" + data);
      }
    })
  });

  $("#boardReadBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/boards/" + $("#boardNo").val(),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
        $("#boardTitle").val(data.boardTitle);
        $("#boardContent").val(data.boardContent);
        $("#boardWriter").val(data.boardWriter);
      },
      error: function (data) {
        alert("error" + data);
      }
    })
  });

  $("#boardRegisterBtn").on("click", function () {
    let boardObject = {
      boardTitle: $("#boardTitle").val(),
      boardContent: $("#boardContent").val(),
      boardWriter: $("#boardWriter").val()
    };
    $.ajax({
      type: "POST",
      url: "/boards",
      data: JSON.stringify(boardObject),
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

  $("#boardDeleteBtn").on("click", function () {
    $.ajax({
      type: "DELETE",
      url: "/boards/" + $("#boardNo").val() + "?writer=" + $("#boardWriter").val(),
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

  $("#boardModifyBtn").on("click", function () {
    let boardNo = $("#boardNo").val();
    let boardObject = {
      boardNo: boardNo,
      boardTitle: $("#boardTitle").val(),
      boardContent: $("#boardContent").val(),
      boardWriter: $("#boardWriter").val()
    };
    $.ajax({
      type: "PUT",
      url: "/boards/" + boardNo,
      data: JSON.stringify(boardObject),
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

  $("#boardResetBtn").on("click", function () {
    $("#boardTitle").val("");
    $("#boardContent").val("");
    $("#boardWriter").val("");
    $("#boardNo").val("");
  });

})
