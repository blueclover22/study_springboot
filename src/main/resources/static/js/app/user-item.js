$(document).ready(function () {

  $("#userItemListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/userItems",
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
      }
    });
  });

  $("#userItemReadBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/userItems/" + $("#userItemNo").val(),
      contentType: "application/json; charset=utf-8",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
      success: function (data) {
        alert(JSON.stringify(data));

        $("#userItemName").val(data.itemName);
        $("#userItemDescription").val(data.description);

        $("#userItemPreview").empty();

        let str =
          "<img src='items/display?itemId=" +
          data.itemId +
          +"&timestamp=" +
          new Date().getTime() +
          "' width='210' height='240'>";

        $("#userItemPreview").append(str);
      }, error: function (xhr, status, error) {
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
      }
    });
  });

  $("#userItemResetBtn").on("click", function () {
    $("#userItemNo").val("");
    $("#userItemName").val("");
    $("#userItemDescription").val("");

  });

  $("#userItemDownloadBtn").on("click", function () {

    let req = new XMLHttpRequest();

    req.open("GET", "/userItems/download/" + $("#userItemNo").val(), true);

    req.responseType = "blob";

    req.setRequestHeader("Authorization", "Bearer " + ACCESS_TOKEN);

    req.onload = function (event) {
      if (req.status === 200) {
        let contentDisposition = req.getResponseHeader("Content-Disposition");

        let downloadFileName = "download"; // 기본값
        if (contentDisposition) {
          let fileNameMatch = contentDisposition.match(/filename\*?=['"]?([^'"\s]+)['"]?/i);
          if (fileNameMatch) {
            downloadFileName = decodeURIComponent(fileNameMatch[1]);
          }
        }

        let blob = req.response;
        let link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = downloadFileName;
        document.body.appendChild(link); // DOM에 추가
        link.click();
        document.body.removeChild(link); // 정리
        window.URL.revokeObjectURL(link.href); // 메모리 정리
      } else {
        alert("다운로드 실패: " + req.status);
      }
    };

    req.onerror = function () {
      alert("다운로드 중 오류가 발생했습니다.");
    };

    req.send();


  })

});
