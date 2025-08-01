$(document).ready(function(){

  // 목록 조회
  $("#codeGroupListBtn").on("click", function(){
    $.ajax({
      type : "GET",
      url : "/codeGroups",
      contentType : "application/json; charset=utf-8",
      dataType : "json",
      success : function(data){
        alert(JSON.stringify(data));
      },
      error : function(data){
        alert(data);
      }
    });


  });

  // 상세조회
  $("#codeGroupReadBtn").on("click", function(){
    let codeGroupCode = $("#groupCode").val();
    $.ajax({
      type : "GET",
      url : "/codeGroups/" + codeGroupCode,
      contentType : "application/json; charset=utf-8",
      dataType : "json",
      success : function(data){
        alert(JSON.stringify(data));
        $("#groupName").val(data.groupName);
      },
      error : function(data){
        alert(data);
      }
    })


  });

  // 등록
  $("#codeGroupRegisterBtn").on("click", function(){

    let codeGroupObject = {
      groupCode : $("#groupCode").val(),
      groupName : $("#groupName").val()
    };

    alert(JSON.stringify(codeGroupObject));

    $.ajax({
      type : "POST",
      url : "/codeGroups",
      data : JSON.stringify(codeGroupObject),
      contentType : "application/json; charset=utf-8",
      dataType : "json",
      success : function(data){
        alert(data);
      },
      error : function(data){
        alert(data);
      }
    });


  });

  // 삭제
  $("#codeGroupDeleteBtn").on("click", function(){
    $.ajax({
      type : "DELETE",
      url : "/codeGroups/" + $("#groupCode").val(),
      contentType : "application/json; charset=utf-8",
      dataType : "json",
      success : function(data){
        alert(data);
      },
      error : function(data){
        alert(data);
      }
    })


  });

  // 수정
  $("#codeGroupModifyBtn").on("click", function(){

    let groupCode = $("#groupCode").val();
    let codeGroupObject = {
      groupCode : groupCode,
      groupName : $("#groupName").val()
    }

    $.ajax({
      type : "PUT",
      url : "/codeGroups/" + groupCode,
      data : JSON.stringify(codeGroupObject),
      contentType : "application/json; charset=utf-8",
      dataType : "json",
      success : function(data){
        alert(data);
      },
      error : function(data){
        alert(data);
      }
    })

  });

  // 리셋
  $("#codeGroupResetBtn").on("click", function(){
    $("#groupCode").val("");
    $("#groupName").val("");
  });

});
