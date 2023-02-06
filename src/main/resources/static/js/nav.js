
 
 //----------------사이드바 script------------------//
      //사이드바 열기 닫기
      let menuBtn = document.getElementById("menu-button");
      let closeBtn = document.getElementById("close-button");
      let sideBar = document.getElementById("sidebar");
      
      menuBtn.addEventListener("click", function(){
        if(sideBar.style.display == "inline-block"){
          sideBar.style.display = "none";
        } else {
          sideBar.style.display = "inline-block";
       }});

      //닫기버튼
      closeBtn.addEventListener("click",function(){
        sideBar.style.display = "none";
      });


      //내게시물 열기 닫기
      let myPost = document.getElementById("my-post");

      myPost.addEventListener("click", function(){
        document.getElementById("panel").classList.toggle("hide");
      });


