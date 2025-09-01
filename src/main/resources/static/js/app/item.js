$(document).ready(function () {
  $("#itemListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/items",
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
      },
      error: function (data) {
        alert("error : " + data);
      },
    });
  });

  $("#itemReadBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/items/" + $("#itemId").val(),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));
        $("#itemName").val(data.itemName);
        $("#itemPrice").val(data.itemPrice);
        $("#itemDescription").val(data.itemDescription);

        $("#preview").empty();
        let str =
          "<img src='items/display?itemId=" +
          data.itemId +
          +"&timestamp=" +
          new Date().getTime() +
          "' width='210' height='240'>";
        $("#preview").append(str);

        $("#preview2").empty();
        let str2 =
          "<img src='items/display?itemId=" +
          data.itemId +
          +"&timestamp=" +
          new Date().getTime() +
          "' width='210' height='240'>";
        $("#preview2").append(str2);
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

  $("#itemRegisterBtn").on("click", function () {
    let itemObject = {
      itemName: $("#itemName").val(),
      itemPrice: $("#itemPrice").val(),
      itemDescription: $("#itemDescription").val(),
    };

    let file = $("input[name=picture]")[0].files[0];
    let file2 = $("input[name=picture]")[1].files[0];

    let formData = new FormData();
    formData.append("file", file);
    formData.append("file2", file2);
    formData.append("item", JSON.stringify(itemObject));

    $.ajax({
      type: "POST",
      url: "/items",
      data: formData,
      processData: false,
      contentType: false,
      dataType: "text",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
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

  $("#itemDeleteBtn").on("click", function () {
    $.ajax({
      type: "DELETE",
      url: "/items/" + $("#itemId").val(),
      contentType: "application/json; charset=utf-8",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
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

  $("#itemModifyBtn").on("click", function () {
    let itemId = $("#itemId").val();
    let itemObject = {
      itemId: itemId,
      itemName: $("#itemName").val(),
      itemPrice: $("#itemPrice").val(),
      itemDescription: $("#itemDescription").val(),
    };

    let file = $("input[name=picture]")[0].files[0];
    let file2 = $("input[name=picture]")[1].files[0];

    let formData = new FormData();

    formData.append("file", file);
    formData.append("file2", file2);
    formData.append("itemObject", JSON.stringify(itemObject));

    $.ajax({
      type: "PUT",
      url: "/items/" + itemId,
      data: formData,
      processData: false,
      contentType: false,
      dataType: "text",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
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

  $("#itemResetBtn").on("click", function () {
    $("#itemName").val("");
    $("#itemPrice").val("");
    $("#itemDescription").val("");
    $("#itemId").val("");

    $("#preview").empty();
    $("#preview2").empty();
  });

  $("#itemDownloadBtn").on("click", function () {
    let req = new XMLHttpRequest();

    req.open("GET", "/items/download/" + $("#itemId").val(), true);

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
  });

  $("#itemBuyBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/items/buy/" + $("#itemId").val(),
      contentType: "application/json; charset=utf-8",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
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
      }
    })
  })

});
