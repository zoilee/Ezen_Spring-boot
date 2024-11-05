$(function(){
      $("#rid").change(function(){
        if($(this).is(":checked")){
            alert("게임방, 관공서등 공공기관에서는 사용하지 마세요.");
        }
      });

    const userIdFromCookie = getCookie('userid');
    if(userIdFromCookie) {
      $("#userid").val(userIdFromCookie);
    }


   /*** 회원가입 스크립트 **/
   $("#emailDomain2").hide();
   $("#emailDomain").change(function(){
      if($(this).val() === 'act'){
        $("#emailDomain2").show().val("");
        $("#emailDomain2").focus();
        $("#emailDomain").attr("readonly", true);
      }else{
        $("#emailDomain2").hide();
        $("#emailDomain").attr("readonly", false);
      }
   }); 
 
    $.validator.setDefaults({
       onkeyup: false,
       onclick: false,
       onfocusout: false,
       showErrors: function(errorMap, errorList){
          if(this.numberOfInvalids()){
             alert(errorList[0].message);
          }
       }
    });
    
    $.validator.addMethod('customPattern', function(value, element, param){
        return this.optional(element) || param.test(value);
    }, "아이디는 알파벳 대, 소문자와 숫자만 허용합니다.");
 
     $('#loginform').validate({
        debug: false, 
        rules: {
            userid: "required",
            userpass: "required"
        },
        messages: {
            userid: "아이디를 입력하세요.",
            userpass: "비밀번호를 입력하세요."
        }
    });
    
    
    $('#registerform').validate({
       rules:{
          userid: {
             required: true,
             minlength: 3,
             maxlength: 9,
             customPattern : /^[a-zA-Z0-9]{3,9}$/,
             remote: "findid"
          },
          usrpass: { required: true, minlength: 5},
          reusrpass: { required: true, equalTo: "#usrpass" },
          username: { required: true },
          emailid: { required: true },
          emailDomain : {
             require_from_group : [1, ".emailgroup"]
          },
          emailDomain2 : {
             require_from_group : [1, ".emailgroup"]
          }
       },
       messages: {
          userid: {
             required: '필수 입력 항목입니다.',
             minlength: '{0}글자 이상 입력하세요.',
             maxlength: '아이디가 너무 길어요. {0}자 이하로 입력하세요.',
             customPattern: '아이디는 알파벳 대,소문자와 숫자만 허용합니다.',
             remote:  $.validator.format("{0} 은(는) 사용중입니다.")
          },
         usrpass: {
             required: '비밀번호를 입력하세요.',
             minlength: '비밀번호는 최소 {0}자 이상이어야 합니다.'
         },
         reusrpass: {
             required: '비밀번호를 확인하세요.',
             equalTo: '비밀번호가 일치하지 않습니다.' // 두 비밀번호가 일치하지 않을 때의 메시지
         },
          username: '이름을 입력하세요.',
          emailid: '이메일을 입력하세요.',
          emailDomain: '이메일을 입력하세요.',
          emailDomain2: '이메일을 입력하세요.'
       },
       submitHandler: function(form){
          const email = $('#emailid').val() + "@" + ($("#emailDomain").val()==='act'? $('#emailDomain2').val():$('#emailDomain').val());
          $("#email").val(email);
          $.ajax({
             url:"findemail",
             data: {
                useremail: email
             },
             success: function(res){
               
                if(res > 0){
                   console.log(res);
                   alert("이미 존재하는 이메일입니다.");
                }else{
                   const tel = $("#tel1").val() + "-" + $("#tel2").val() + "-" + $("#tel3").val();
                   $("#tel").val(tel);   
                   form.submit();
                }
             }, 
             error: function(){
                alert("이메일 검증중 오류가 발생했습니다.");
             }
          });
                                   
       }
    });
/*
//my work
    //페이징 구현
    
   	
    
    const totalPosts =dbBoard.length;  // 전체 포스트 수
    console.log(dbBoard[1]);

    console.log(totalPosts);
	const postsPerPage = 10; //페이지당 보여질 포스트 수
	const displayPageNum = 10;	// 보여질 페이징 수
	let currentPage = 1;
	const totalPages = ((totalPosts - 1)/postsPerPage)+1 // 총 페이징 수	
	


		
	
	function displayPosts(page) {
		const paging = $('#display')   //tbody
		const table = $('#table');	//table외부
		paging.empty();
		table.empty();
	

	// 페이징 구현
	const startIndex = (page - 1) * postsPerPage;
	const endIndex = Math.min(startIndex + postsPerPage, totalPosts);
	
	for (let i = startIndex; i< endIndex; i++){
		const post = dbBoard[i];
		
		const td = $(`
			<tr>
                <td id="posts">${dbBoard[i].num}</td>
                <td class="ellipsis"><a href="bbs.jsp?mode=view&num=${dbBoard[i].num}">${dbBoard[i].title}</a></td>
                <td class="ellipsis">${dbBoard[i].writer}</td>
                <td>${dbBoard[i].viewDate}</td>
                <td>${dbBoard[i].count}</td>
            </tr> 
		`);
			
        paging.append(td);
    }
    //페이징 버튼
    
    const endPages = Math.min(Math.ceil(currentPage / displayPageNum) * displayPageNum, totalPages); // 페이지 마지막 번호
	const startPages = Math.max(endPages - displayPageNum + 1, 1); // 페이지 시작 번호
    
    // Prev 버튼 생성
    const prev = $('<li></li>').text("Prev");
    if (page === 1) {
        prev.addClass('hidden');
    } else {
        prev.on('click', function(){
        	currentPage = page - 1 ;
            displayPosts(currentPage);
        });
    }
    table.append(prev);

    // 페이지 번호 버튼 생성
    for (let i = startPages; i <= endPages; i++) {
        const li = $('<li></li>').text(i);
        if (i === page) {
            li.addClass('active');
        }
        li.on('click', function() {
            currentPage = i;
            displayPosts(i);
        });
        table.append(li);
    }

    // Next 버튼 생성
    const next = $('<li></li>').text("Next");
    if (page === totalPages) {
        next.addClass('hidden');
    } else {
        next.on('click', function(){
        	currentPage = page + 1 ;
            displayPosts(currentPage);
        });
    }
    table.append(next);
	
        
    }
    displayPosts(currentPage);
    */
}); //jquery



function setCookie(name, value, exp){
    const date = new Date();
    date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000); // 쿠키 유효 기간 설정
    document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
}

function getCookie(name){
   const value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
   return value? value[2]:null;
}

function delCookie(name){
   document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
}


