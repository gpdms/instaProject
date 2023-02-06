
 
 //-------------헤더script---------------------------------------
      //스크롤 시 헤더 줄어듦
      window.onscroll = function () { scrollFunction() };
      function scrollFunction() {
        if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {

          document.getElementById("top").style.height = "50px";
          document.getElementById("settings").style.display = "none";
          document.getElementById("user-name").style.display = "none";

          let elementProfile = document.getElementById("profile");
          elementProfile.style.cssText = "width: 20%; height: 50px; margin-top: 0; float: left; margin-left: 500px;"

          let elementImg = document.getElementById("profile-img");
          elementImg.style.cssText = "width: 46px; height: 46px; margin-top: 2px;"

          let elementName = document.getElementById("profile-name");
          elementName.style.cssText = "line-height: 46px; font-size: 18px; margin-top: 0;"

          let elementUpload = document.getElementById("upload-button");
          elementUpload.style.cssText = "width:46px; height: 46px; margin-top: 1px; margin-right: 100px;"

          // 마우스오버 이벤트 제거
          elementImg.addEventListener("mouseover", function () {
            document.getElementById("arrow-img").style.display = "none";
          });

          elementUpload.addEventListener("mouseover", function () {
          document.getElementById("arrow-upload").style.display = "none";
          });


          //스크롤 위로 올리면 헤더 다시 원래대로
        } else {
          document.getElementById("top").style.height = "260px";
          document.getElementById("settings").style.display = "inline";
          document.getElementById("user-name").style.display = "inline";

          let elementProfile = document.getElementById("profile");
          elementProfile.style.cssText = "display: inline-flex;margin-top: 45px; margin-left: 400px; width: 30%;"

          let elementImg = document.getElementById("profile-img");
          elementImg.style.cssText = "width: 150px; height: 150px;"

          let elementName = document.getElementById("profile-name");
          elementName.style.cssText = "line-height: 150px; font-size: 24px; margin-left: 30px;"

          let elementUpload = document.getElementById("upload-button");
          elementUpload.style.cssText = "width:120px; height: 120px; margin-top: 65px; margin-right: 200px;"

          //마우스오버 이벤트 생성
          elementImg.addEventListener("mouseover", function () {
            document.getElementById("arrow-img").style.display = "inline-block";
          });
          elementUpload.addEventListener("mouseover", function () {
            document.getElementById("arrow-upload").style.display = "inline-block";
          });

        }
      };

      //모달부분
      let body = document.querySelector("body");
      let modal = document.querySelector('.modal');
      let settings = document.getElementById("settings");
      let profileName = document.getElementById('profile-name');

      function modalOpen() {
        modal.style.display = 'block';
        body.style.overflow = "hidden";
      }
      function modalClose() {
        modal.style.display = "none";
        body.style.overflow = "auto";
      }

      // 클릭시 모달오픈
      settings.addEventListener('click', modalOpen);
      profileName.addEventListener('click', modalOpen);

      // 외부 클릭시 모달창닫음
      window.addEventListener('click', (e) => {
        e.target === modal ? modalClose() : false
      })


      //마우스 오버시 말풍선
      //프로필이미지 오버시 말풍선
      let profileImg = document.getElementById("profile-img");

      profileImg.addEventListener("mouseover", function () {
        let arrowImg = document.getElementById("arrow-img");
        arrowImg.style.display = "inline-block";
      });
      profileImg.addEventListener("mouseleave", function () {
        let arrowImg = document.getElementById("arrow-img");
        arrowImg.style.display = "none";
      });

      //업로드버튼 오버시 말풍선
      let uploadButton = document.getElementById("upload-button");

      uploadButton.addEventListener("mouseover", function () {
        let arrowUpload = document.getElementById("arrow-upload");
        arrowUpload.style.display = "block";
      });
      uploadButton.addEventListener("mouseleave", function () {
        let arrowUpload = document.getElementById("arrow-upload");
        arrowUpload.style.display = "none";
      });
