$(document).ready(function () {

  function getOriginalName(fileName) {
    let index = fileName.indexOf("_") + 1;
    return fileName.substring(index);
  }

  $(".uploadList").on("click", "span", function (e) {
    $(this).parent("div").remove();
  });

  $("#pdsInputFile").on("change", function (e) {

    let files = e.target.files;
    let file = files[0];
    let formDate = new FormData();

    formDate.append("file", file);

    $.ajax({
      type: "POST",
      url: "/pds/upload",
      data: formDate,
      processData: false,
      contentType: false,
      dataType: "text",
      headers: {
        Authorization: "Bearer " + ACCESS_TOKEN,
      },
      success: function (data) {
        alert("success : " + data);
        let str = "<div><a href='/pds/download/?fullName=" + data + "'>" + getOriginalName(file.name) + "</a><span>X</span></div>";
        $(".uploadedList").append(str);
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
    })
  });

  $("#pdsItemListBtn").on("click", function () {
    $.get("/pds", function (data) {
      alert(JSON.stringify(data));
    })
  });

  $("#pdsItemReadBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/pds/" + $("#pdsItemNo").val(),
      contentType: "application/json; charset=UTF-8",
      dataType: "json",
      success: function (data) {
        alert("success : " + data);

        $("#pdsItemName").val(data.itemName);
        $("#pdsItemDescription").val(data.description);

        $(".uploadedList").empty();

        $.getJSON("/pds/attach/" + data.itemId, function (list) {

          $(list).each(function () {

            let data = this;

            let str = "<div><a href='/pds/download/?fullName=" + data + "'>" + getOriginalName(data) + "</a></div>";
            $(".uploadedList").append(str);
          });
        });

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
        )
      }
    })
  });

  $("#pdsItemRegisterBtn").on("click", function () {
    let fileNames = [];

    $(".uploadedList a").each(function (idx) {
      let value = $(this).attr("href");
      value = value.substring(24)

      fileNames[idx] = value;
    });

    let itemObject = {
      itemId: $("#pdsItemId").val(),
      itemName: $("#pdsItemName").val(),
      description: $("#pdsItemDescription").val(),
      fileNames: fileNames
    };

    $.ajax({
      type: "POST",
      url: "/pds",
      data: JSON.stringify(itemObject),
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
        )
      }
    })
  });

  $("#pdsItemDeleteBtn").click(function () {
    $.ajax({
      type: "DELETE",
      url: "/pds/" + $("#pdsItemId").val(),
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
        )
      }
    })
  });

  $("#pdsItemModifyBtn").on("click", function () {

    let itemNo = $("#pdsItemId").val();

    let fileNames = [];
    $(".uploadedList a").each(function (idx) {
      let value = $(this).attr("href");
      value = value.substring(24)
      fileNames[idx] = value;
    })

    let itemObject = {
      itemNo: itemNo,
      itemName: $("#pdsItemName").val(),
      description: $("#pdsItemDescription").val(),
      fileNames: fileNames
    };

    $.ajax({
      type: "PUT",
      url: "/pds/" + itemNo,
      data: JSON.stringify(itemObject),
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
        )
      }
    })
  });

  $("#pdsItemResetBtn").on("click", function () {
    $("#pdsItemId").val("");
    $("#pdsItemName").val("");
    $("#pdsItemDescription").val("");
    $(".uploadedList").empty();
  });

})
