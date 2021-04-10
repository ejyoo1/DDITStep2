/**
 * 자바스크립트 실행
 */
loadJQuery();

/**
 * 현재 위치에 JQuery 추가하는 메서드
 */
function loadJQuery() {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";		  
    oScript.src = "../../js/jquery-3.6.0.js";	
    document.getElementsByTagName("head")[0].appendChild(oScript);
//    console.log("JQuery 업로드 완료");
}

/**
 * get방식으로 전송된 url에서 키에 해당하는 하나의 값을 추출하는 메서드
 * @param strUrl 전체 url
 * @param strKey 추출하고자하는 키값
 * @returns strKey의 값
 */
function getValue(strUrl, strKey){
	var params = url.substr(url.indexOf("?")+1);
	var arrparams = params.split("&");
	var result = "";
	$(arrparams).each(function(){
		if(this.indexOf(strKey) != -1){
			result = this;
		}
	});
	result = result.split("=")[1];
	return result;
}

/**
 * get방식으로 전송된 url에서 키에 해당하는 여러개의 값을 추출하는 메서드
 * @param strUrl 전체url
 * @param strKey 추출하고자하는 키값
 * @returns strkey의 값들을 나열한 String 값 (V,V,V 형식)
 */
function getSelectValue(strUrl, strKey){
	var params = url.substr(url.indexOf("?")+1);
	var arrparams = params.split("&");
	var resultArr = [];
	var result = "";
	$(arrparams).each(function(){
		if(this.indexOf(strKey) != -1){
			resultArr.push(this);
		}
	});
	$(resultArr).each(function(){
		result += this.split("=")[1] + ", ";
	});
	result = result.substr(0, result.length-2);
	
	return result;
}

/**
 * 공백검사(미구현)
 * @returns
 */
function isBlank(){}

/**
 * 빈값 검사
 * @returns [false:공백임, true:공백아님]
 */
function isEmpty(param){
	if(param == ""){
		return false;
	}else{
		return true;
	}
}

/**
 * 글자 수 최대 길이 체크하는 메서드
 * @param length 희망하는 글자 최대 길이
 * @param param 체크할 문자
 * @returns 체크한 결과값 [false: 최대 길이에 맞지않음, true: 최대 길이에 맞음]
 */
function checkLength(length,param){
	if(param.length != length){
		return false;
	}else{
		return true;
	}
}

/**
 * 하이픈 제거하는 메서드
 * @param param 하이폰 제거할 내용
 * @returns 하이폰 제거한 내용
 */
function delHyphen(param){
	var temp = param.replace("-","");
	return temp;
}

/**
 * 유효성 체크 메서드
 * @param regExp 유효성 체크 정규식
 * @param param 정규식 체크할 문자
 * @returns 정규식 체크 결과 [false:정규식 불일치, true: 정규식 일치]
 */
function checkRegExp(regExp, param){
	if(param.match(regExp)){
		return false;
	}else{
		return true;
	}
}

/**
 * 주민번호 결과를 계산하는 메서드 (개선 필요 : 추후 변수로 값을 받도록 변경해야 함.)
 * @returns
 */
function checkRRN(){
  var rrNumber = $("#rrNumber").val();
  var userNumber, ssn, temp;
  var regExp = ["[^0-9]","\d{13}"]; //[숫자체크,길이체크]
  
  if(!isEmpty(rrNumber)){
	  alert("주민등록 번호는 필수 입력입니다.");
	  return;
  }
  
  if(rrNumber.indexOf("-") != -1){
	 temp = delHyphen(rrNumber);
  }else{
	 temp = rrNumber;
  }
  
  if(!checkRegExp(regExp[0], temp)){
	 alert("주민등록번호는 숫자만 입력해야 합니다.");
	 return;
  }
  
  if(!checkRegExp(regExp[1],temp)){
	  alert("주민등록번호는 13자리입니다.");
	  return;
  }
  
  ssn = temp;
    
  var arr_ssn = [];
  var compare = [2,3,4,5,6,7,8,9];
  var sum = 0;
  
  // 공식: M = (11 - ((2×A + 3×B + 4×C + 5×D + 6×E + 7×F + 8×G + 9×H + 2×I + 3×J + 4×K + 5×L) % 11)) % 10
  for(var i = 0; i < 13 ; i++){
    arr_ssn[i] = ssn.substring(i,i+1);
  }
  
  //1. 총 합을 구한다.
  for(var i = 0; i < 12 ; i++){
    
    if(i < 8){
      //compare index 0~7
      sum = sum + (arr_ssn[i] * compare[i]);
    } else{
      //index 8~14
      sum = sum + (arr_ssn[i] * compare[i-8]);
    }
  }
  
  //2. 총합에서 11로 나눈 나머지를 구한다.
  //sum = sum % 11;
    
  //3. 11에서 2번의 결과값을 뺀다.
  //sum = 11 - sum;
    
  //4. sum에서 10을 나눈 나머지를 구한다.
  //sum = sum % 10;
  
  //5. 결과 확인
  //11로 나눈 나머지와 주민번호의 맨 마지막 값이 같으면 맞는 번호이다
  if((11-(sum % 11)) % 10 != arr_ssn[12]){
	  alert("올바른 주민등록 번호를 입력하여 주세요.");
	  return;
  }else{
	  alert("올바른 주민등록 번호입니다.");
  }                                      
}

/**
 * 하나의 값을 특정 형식으로 변경하는 메서드(DB 데이터 가공)(구현중)
 * @param val 변경할 값
 * @param type 변경할 유형
 * @returns
 * hpno(휴대전화) : [★DB 저장 시 : 01012341234, 그외 : 010-1234-1234, 010-12341234, 010 1234 1234]
 * dateNo(날짜) : [93-12-16, 93/12/06 , 2020/04/08 , 2020-04-08, 2020년 04월 08일 931206(6자), 19931206(8자), 그외 변경안함.]
 */

//function format(val, type){//JSP 미적용으로 변수 따로 생성함.(개선해야 함.)
function format(){
	var hpNumber 	= $("#hpNumber").val();
	var date 		= $("#date").val();
	
	var hpNumtype 	= "hpNo";
	var datetype 	= "dataNo";
	
	var hpNumExp 	= "/(\d{3})(\d{3,4})(\d{4})/";
	var dateExp		= "//";	
	
	var hpNumFormat = "$1-$2-$3";
	var dateFormat	= "$1/$2/$3"; // "$1년$2월$3일"
	
	//숫자
	if(type == hpNumtype){
		hpNumber = hpNumber.replaceAll("-","").replaceAll(" ","");
		hpNumber = hpNumber.replace(hpNumExp,hpNumFormat);//val.replace(정규식,이형태로); ==> $1 : 1번파라미터() $2 : 2번파라미터()
		console.log(hpNumber);
		return hpNumber;
	}
	
	//날짜
	if(type == datetype){
		val = val.replace("-","").replaceAll(" ","").replaceAll("/","");
		if(val.length == 6 || val.length == 8){
			val.replace(dateExp,dateFormat);
		}else{
			console.log("변경불가");
			return val;
		}
	}
}

/**
 * 특정 형식으로 일괄 변경하는 메서드 (미구현)
 * @returns
 * 필요기능 : 성공 count
 */
function fomatAll(){}
