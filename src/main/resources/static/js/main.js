
 
 //-------------메인 script-------------------------------      
 let iuTab = document.getElementById("tab-iu");
 let tripTab = document.getElementById("tab-trip");
 let allTab = document.getElementById("tab-all");

 let center = document.getElementsByClassName("center")[0];
 let content = document.getElementsByClassName("content");

 //탭관련 변수
 const tabList = document.querySelectorAll(".dep1 .panel li");
 let activeCont = ""; // 현재 활성화 된 컨텐츠 (기본:#tab-all 활성화)

 //이미지 
 for (let i = 0; i < 24; i++) {
   let imgTrip = `<article ><img src="img여행/${i}.jpg" alt="여행${i
     }" onclick="window.open(this.src)"></article>`;
   content[0].insertAdjacentHTML("beforeend", imgTrip);
 }
 for (let i = 0; i < 24; i++) {
   let imgIu = `<article ><img src="img아이유/${i}.jpg" alt="아이유${i}" onclick="window.open(this.src)"></article>`;
   content[0].insertAdjacentHTML("beforeend", imgIu);
 }

 for (let i = 0; i < 24; i++) {
   let imgIu = `<article><img src="img아이유/${i}.jpg" alt="아이유${i}" onclick="window.open(this.src)"></article>`;
   content[1].insertAdjacentHTML("beforeend", imgIu);
 }

 for (let i = 0; i < 24; i++) {
   let imgTrip = `<article><img src="img여행/${i}.jpg" alt="여행${i}" onclick="window.open(this.src)"></article>`;
   content[2].insertAdjacentHTML("beforeend", imgTrip);
 }


 // 더보기
 let btn = document.getElementById("btn_open");
 let count = 0;
 function button_onclick() {
   count++;
   if (count == 1) {
     center.style.height = "1150px";
   } else if (count == 2) {
     if (activeCont == "#tab-iu" || activeCont == "#tab-trip") {
       btn.classList.add("noContent");
       btn.innerText = "No Content";
     }
     center.style.height = "1750px";
   } else if (count == 3) {
     center.style.height = "2325px";
   } else if (count == 4) {
     center.style.height = "2900px";
   } else if (count == 5) {
     center.style.height = "3500px";
     btn.classList.add("noContent");
     btn.innerText = "No Content";
   }
 }

 //탭 전환 (참고 : https://abcdqbbq.tistory.com/88)
 for (var i = 0; i < tabList.length; i++) {
   tabList[i]
     .querySelector(".btn")
     .addEventListener("click", function (e) {
       //탭 이동시, 클릭 횟수 초기화
       count = 0;
       e.preventDefault();

       for (var j = 0; j < tabList.length; j++) {
         //모든 컨텐츠 display:none 처리
         content[j].style.display = "none";
         //탭 이동시, 더보기 접기
         center.style.height = "600px";
         btn.classList.remove("noContent");
         btn.innerText = "더보기";
       }

       //this = 현재 누른 탭      
       console.log(this);
       //탭 클릭시 컨텐츠 전환
       activeCont = this.getAttribute("href"); //href의 속성 얻는다 => id를 얻음
       console.log(activeCont);
       document.querySelector(activeCont).style.display = "block";
     });   
 };