$(document).ready(function () {
  $("#coinChargeListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/coins",
      contentType: "application/json; charset=utf-8",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
      success: function (data) {
        alert(JSON.stringify(data));
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

  $("#coinChargeBtn").on("click", function () {
    $.ajax({
      type: "POST",
      url: "/coins/charge/" + $("#coinAmount").val(),
      contentType: "application/json; charset=utf-8",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
      success: function (data) {
        alert(JSON.stringify(data));
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

  $("#coinResetBtn").click(function () {
    $("#coinAmount").val("");
  });
});
