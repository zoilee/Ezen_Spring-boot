$(function(){
   
   //갤러리 로딩 
   $(window).on('load', function(){
      $('.photos-gallery').pycsLayout({
         pictureContainer: ".picture",
         gutter:5,
         idealHeight: 200
      });
   }); 

});