$(document).ready(function () {

  // 목록 조회
  $("#codeDetailListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/codeDetails",
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

  // 상세 조회
  $("#codeDetailReadBtn").on("click", function () {

    $.ajax({
      type: "GET",
      url: "/codeDetails/" + $("#codeGroupCode").val() + "/" + $("#codeValue").val(),
      contentType: "application/json; charset=utf-8",
      headers: {
        "Authorization": "Bearer " + ACCESS_TOKEN
      },
      dataType: "json",
      success: function (data) {
        alert(JSON.stringify(data));

        $("#codeGroupCode").val(data.groupCode);
        $("#codeValue").val(data.codeValue);
        $("#codeName").val(data.codeName);
      },
      error: function (data) {
        alert(data);
      }
    })

  });

  // 등록
  $("#codeDetailRegisterBtn").on("click", function () {

    let codeGroupObject = {
      groupCode: $("#codeGroupCode").val(),
      codeValue: $("#codeValue").val(),
      codeName: $("#codeName").val()
    };

    alert(JSON.stringify(codeGroupObject));

    $.ajax(
      {
        type: "POST",
        url: "/codeDetails",
        data: JSON.stringify(codeGroupObject),
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
      }
    )

  });

  // 삭제
  $("#codeDetailDeleteBtn").on("click", function () {
    $.ajax({
      type: "DELETE",
      url: "/codeDetails/" + $("#codeGroupCode").val() + "/" + $("#codeValue").val(),
      contentType: "application/json; charset=utf-8",
      headers: {
        "Authorization": "Bearer " + ACCESS_TOKEN
      },
      dataType: "json",
      success: function (data) {
        alert(data);
      },
      error: function (data) {
        alert(data);
      }
    })


  });

  // 수정
  $("#codeDetailModifyBtn").on("click", function () {
    let codeGroupCode = $("#codeGroupCode").val();
    let codeValue = $("#codeValue").val();
    let codeName = $("#codeName").val();

    let codeGroupObject = {
      groupCode: codeGroupCode,
      codeValue: codeValue,
      codeName: codeName
    };

    $.ajax({
      type: "PUT",
      url: "/codeDetails/" + codeGroupCode + "/" + codeValue,
      data: JSON.stringify(codeGroupObject),
      headers: {
        "Authorization": "Bearer " + ACCESS_TOKEN
      },
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        alert(data);
      },
      error: function (data) {
      }
    })

  });


  // 리셋
  $("#codeDetailResetBtn").on("click", function () {
    $("#codeGroupCode").val("");
    $("#codeValue").val("");
    $("#codeName").val("");
  });

  // 초기값 로딩
  $.getJSON("/codes/codeGroup", function (list) {

    $(list).each(function () {
      let str = "<option value='" + this.value + "'>" + this.label + "</option>";
      $("#codeGroupCode").append(str);
    })

  });

});
