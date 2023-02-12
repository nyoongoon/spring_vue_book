Vue.directive('focus', {
    // 바인딩된 요서가 DOM으로 삽입될 때
    inserted: function (el){
        el.focus();
    }
});