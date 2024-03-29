
# Vue 인스턴스
- 루트 Vue 인스턴스와 컴포넌트 인스턴스로 구성됨.
```vue
new Vue({/* options */}) 
```
- 위와 같이 options객체에 애플리케이션을 기술하고 Vue.js가 이 객체를 가지고 Vue 인스턴스를 초기화한다.

## Vue 인스턴스 라이프 사이클 훅
- 라이프 사이클 훅은 특별하게 인스턴스의 라이프 사이클 동안 각 단계별 로직을 정의할 수 있는 능력 제공.
- beforeCreate
- created
- beforeMount
- mounted : DOM이 업데이트 된 후에 호출. 이 시점에서 사용자는 UI와 사용자굥ㅇ할 수 있으며 인스턴스는 완전한 기능을 수행함.
- beforeUpdate
- updated
- activated
- deactivated
- beforeDestory
- destoryed
- errorCaptured


# 믹스인(Mixins)
- 믹스인은 코드를 재사용할 수 있는 또다른 방법.ㅎ
- 모든 컴포넌트의 옵션을 포함할 수 있는 자바스크립트 객체.
- Vue는 컴포넌트의 options 객체에 믹스인을 혼합. 
- 믹스인과 컴포넌트가 같은 옵션을 포함하는 경우 값에 따라 다른 전략으로 병합.
- 믹스인의 옵션과 컴포넌트의 옵션이 병합되면서 다소의 모호함을 만들어 코드의 가독성을 떨어뜨릴 수도 있음.
## 전역 or 로컬 믹스인
- 전역 또는 로컬로 사용할 수 있다.
- 믹스인은 **전역**으로 적용하면 이후 생성되는 **모든 Vue 인스턴스에 자동으로 영향**을 미침 !!**
- cf)컴포넌트, 필터, 지시자는 전역으로 등록되어 있어도 선언해서 사용해야함


# 템플릿 추가하기
- 템플릿을 부착 지점인 <div id="app"></div>내에 직접 넣기.
- {{}} 와 같은 이중 중괄호 태그를 보간할때 출력결과와 해당 데이터 사이에 데이터 바인딩을 생성.



# 컴포넌트 
- Vue컴포넌트는 Vue인스턴스이기도 함.
- Vue컴포넌트는 생성 중에 Vue 인스턴스와 같은 options 객체를 받음.
- 컴포넌트를 전체 애플리케이션에서 활용할 수 있게 전역을 등록 가능
```
Vue.component(id, [definitnion]) 사용 //아이디, //options객체이거나options객체를 반환하는 함수
```


# options 객체
## data 프로퍼티
## methods 프로퍼티
- options 객체의 methods 프로퍼티
- 단일 객체를 가짐.
- 이 객체에서 사용하는 모든 메소드를 담음.
- 이 메소드 내에서는 ***this로 data 프로퍼티에 접근***할 수 있음.
- 화살표 함수구문 사용X, this로 Vue 인스턴스에 접근할 수 없게 됨

## computed 프로퍼티
- data 객체 내 프로퍼티와 달리 계산된 값.
- Vue.js는 computed 프로퍼티에 종속된 대상을 추적하고 종속된 대상이 변경될 때 프로퍼티의 값을 업데이트함.
- options 객체 내의 computed 프로퍼티 객체의 메소드로 정의함

## props 프로퍼티
- Vue.js에서 컴포넌트는 자체의 고립된 스코프를 가짐 -> props를 사용하여 해결 !
- props 프로퍼티로 컴포넌트에 전달할 수 있는 데이터를 정의 가능.
- props 프로퍼티는 배열 또는 객체를 값으로 가짐.
- **부모 컴포넌트 -> DATA -> 자식 컴포넌트**
- 자식 컴포넌트에서 props 옵션을 선언하고, 부모 컴포넌트에서 props 바인딩하여 채워넣음. 
```vue
//부모
<message-list :items="messages" @delete="deleteMessage"></message-list> //부모에서 :items로 바인딩 (v-bind)
```
```javascript
export default{
    name: 'MessageList',
    template: `<ul><message-list-item v-for="item in items" :item="item"
                :key="item.id" @delete="deleteMessage(item)"></message-list-item></ul>`,
    props: { // props 설정 !
        items: {
            type: Array,
            required: true
        }
    }
}
```


# 지시자
- 지시자는 표현식의 값이 변경될 때 이에 반응해 DOM에 변경 사항을 적용함.
## 사용자 정의 지시자
- 전역 등록 : 지시자 객체를 생성하고 이를 Vue.directive()를 이용
- 로컬 등록 : 컴포넌트의 directives 프로퍼티를 이용
## 지시자 훅 함수
- 지시자 외부에서 일어나는 일들을 지시자에 전달할 수 있는 훅 함수를 지시자 정의 객체 내에 추가할 수 있음.
- bind, inserted, update, componentUpdated, unbind
## v-model
- 양방향 바인딩 생성.
```vue
<textarea v-model="newMessage" placeholder="Leave a message">
</textarea>
<!--textarea의 요소와 data객체의 newMessage 프로퍼티 사이에 양방향 바인딩.-->
```
## v-bind (":"(콜론))
- v-bind 지시자로 class와 style 같은 HTML 요소의 내장된 속성을 연결할 수 있음.
- 또한 그것을 Vue의 사용자 정의 컴포넌트의 프로퍼티를 연결하는 데 사용 가능.
## v-cloak
- Vue.js는 생성된 DOM이 준비되면 v-cloak 지시자를 제거함.
## v-for
- **v-for 지시자로 렌더링된 컴포넌트 리스트는 명시적인 키를 가져와야함 !!!**.
- Vue는 리스트에서 어떤 아이템이 변경됐는지 추적해 DOM에서 해당 부분만 업데이트할 수 있도록 리스트에서 아이템의 고유 아이디가 될 수 있는 키를 요청.


# 컴포넌트
- 컴포넌트를 이용하면 HTML 요소를 확장하고 추가 로직을 제공할 수 있어 그것들을 재사용할 수 있음
- Vue 컴포넌트는 Vue 인스턴스이기도 함 -> 컴포넌트는 생성 중에 인스턴스와 같은 options 객체를 받음.

## 전역 컴포넌트
- 컴포넌트를 전체 애플리케이션에서 활용할 수 있게 **전역으로 등록** 할 수 있음.
- Vue.component(id, \[definition\])
- 첫 번째 인자는 컴포넌트의 id (== 템플릿에서 사용할 태그 이름)
- 두 번째 인자는 컴포넌트에 대한 정의로 options 객체이거나 options 객체를 반환하는 함수.
- 컴포넌트를 다른 Vue 인스턴스의 범위에서만 활용할 수 있게 로컬로 등록할 수 있음.

## 컴포넌트 스코프
- Vue에서 컴포넌트는 컴포넌트 자체의 고립된 스코프를 가짐.
- props 프로퍼티로 컴포넌트에 전달할 수 있는 데이터를 정의하여 한계를 극복.

# Vue 메소드
## $emit()
- 현재 인스턴스의 이벤트를 트리거 할 수 있는 메소드
- 자식 컴포넌트에서 $emit('이벤트명', '데이터')를 통해 부모 컴포넌트로 이벤트를 보냄(트리거 함)
- **자식 -> 이벤트 -> 부모**
```vue
<!--부모-->
<message-list :items="messages" @delete="deleteMessage"></message-list>
```
```javascript
//자식
export default {
    name: MessageList,
    //...,
    methods: {
        deleteMessage(message){
            this.$emit('delete', message); //부모 컴포넌트로 보내지는 이벤트
        }
    }
}
```

# 필터
- 필터를 이용해 이중 중괄표 보간법 혹은 v-bind 표현법을 이용할 때 텍스트 형식을 지정.
- 필터는 표현식의 값을 첫 번째 인자로 가지는 자바스크립트 함수.
- 전역 등록 : Vue.filter()로 전역 등록
- 로컬 등록 : 컴포넌트 options 객체의 filters 프로퍼티를 로컬로 등록.

# 플러그인
- Vue 애플리케이션에서 플러그인을 사용하려면 플러그인을 모듈로 가져와서 Vue.use()를 호출.
- install() 메소드를 가지는 일반객체를 만들어서 플러그인을 만든다.
- 첫 번째 인자는 Vue 생성자로 여기에 모든 확장된 기능을 적용한다.
- 두 번째 인자는 options 객체이며 플러그인을 구성하는 데 사용할 옵션을 정의할 수 있다.
- install() 메소드 내부에서 정적 메소드 또는 프로퍼티를 Vue 생성자에 추가할 수 있음.