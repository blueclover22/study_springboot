$(document).ready(function () {

  // 목록 조회
  $("#codeDetailListBtn").on("click", function () {
    $.ajax({
      type: "GET",
      url: "/codeDetails",
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

  // 상세 조회
  $("#codeDetailReadBtn").on("click", function () {

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


  });

  // 수정
  $("#codeDetailModifyBtn").on("click", function () {


  });


  // 리셋
  $("#codeDetailResetBtn").on("click", function () {

  });

  // 초기값 로딩
  $.getJSON("/codes/codeGroup", function (list) {

    $(list).each(function () {
      let str = "<option value='" + this.value + "'>" + this.label + "</option>";
      $("#codeGroupCode").append(str);
    })

  });

});
