/**
 * 
 */
 const tag = $(".storeList-tag-content>ul>li");
tag.on("click",function(){
    const index = tag.index(this);
    if(index < tag.length){
        for(i=0; i<tag.length;i++){
            $(tag).eq(i).css("color","black");
        }
    }
    
    $(tag).eq(index).css("color","#FFAE6D");
});

const sort = $(".storeList-sort-content>ul>li");
sort.on("click",function(){
    const sortindex = sort.index(this);
    if(sortindex < sort.length){
        for(i=0; i<sort.length;i++){
            $(sort).eq(i).css("color","black");
        }
    }
    $(sort).eq(sortindex).css("color","#FFAE6D");
});

const locationTag = $(".storeList-map-content>div");
locationTag.on("click",function(){
    const index = locationTag.index(this);
    const TagText = document.querySelector(".storeList-map-content>div>p");

    if(index < locationTag.length-1){
        const locationTagText = locationTag.eq(index).text();
        TagText.innerText = locationTagText+" 지역 맛집 찾기";
    }
});