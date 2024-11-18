/*
// 삭제 기능
const deleteButton = document.getElementById('delete-btn'); // html id속성값을 'delete-btn'으로 설정한 엘리먼트를 찾는 변수

if (deleteButton) {
    deleteButton.addEventListener('click', event => { // 클릭 이벤트 발생 시 메서드 실행
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, { // fetch() 메서드를 통해 /api/articles/DELETE 요청을 보냄.
            method: 'DELETE'
        })
        .then(() => { // fetch 메서드 정상 작동 시 동작
           alert('삭제가 완료되었습니다.'); // 웹 브라우저 화면에 삭제 완료 알림 팝업창 띄움.
           location.replace('/articles'); // 실행 시 사용자 웹 화면을 현재 주소를 기반으로 옮겨줌.
        });
    });
}

// 수정 기능
// id가 modify-btn인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn'); // html id속성값을 'modify-btn'으로 설정한 엘리먼트를 찾는 변수

if (modifyButton) {
    // 클릭 이벤트 감지 시 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id'); // URLSearchParams 객체로부터 id 값을 가져옴.

        // id가 title, content인 엘리먼트의 값을 가져와 fetch() 메서드를 통해 수정API로 /api/articles/PUT 요청을 보냄.
        fetch(`/api/articles/${id}`, {
            method: 'PUT',
            headers: { // 요청 형식 지정
                "Content-Type": "application/json"
            },
            body: JSON.stringify({  // HTML에 입력한 데이터를 JSON 형식으로 변환해 보냄.
                title: document.getElementById('title').value,
                content: document.getElementById('content').value
            })
        })
        .then(() => { // 마무리 작업 수행
            alert('수정이 완료되었습니다.');
            location.replace(`/articles/${id}`);
        });
    });
}

// 생성 기능
// id가 create-btn인 엘리먼트
const createButton = document.getElementById('create-btn'); // html id속성값을 'create-btn'으로 설정한 엘리먼트를 찾는 변수

if (createButton) {
    //클릭 이벤트 감지 시 생성 API 요청
    createButton.addEventListener("click", (event) => {

       // id가 title, content인 엘리먼트의 값을 가져와 fetch() 메서드를 통해 생성 API로 /api/articles/POST 요청을 보냄.
       fetch("/api/articles", {
           method: "POST",
           headers: { // 요청 형식 지정
               "Content-Type": "application/json"
           },
           body: JSON.stringify({  // HTML에 입력한 데이터를 JSON 형식으로 변환해 보냄.
               title: document.getElementById("title").value,
               content: document.getElementById("content").value
           }),
       })
       .then(() => { // 마무리 작업 수행
           alert('등록이 완료되었습니다.');
           location.replace("/articles");
       });
    });
}*/

// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            location.replace('/articles');
        }

        function fail() {
            alert('삭제 실패했습니다.');
            location.replace('/articles');
        }

        httpRequest('DELETE',`/api/articles/${id}`, null, success, fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        })

        function success() {
            alert('수정 완료되었습니다.');
            location.replace(`/articles/${id}`);
        }

        function fail() {
            alert('수정 실패했습니다.');
            location.replace(`/articles/${id}`);
        }

        httpRequest('PUT',`/api/articles/${id}`, body, success, fail);
    });
}

// 생성 기능
const createButton = document.getElementById('create-btn');

if (createButton) {
    // 등록 버튼을 클릭하면 /api/articles로 요청을 보낸다
    createButton.addEventListener('click', event => {
        body = JSON.stringify({
            title: document.getElementById('title').value,
            content: document.getElementById('content').value
        });
        function success() {
            alert('등록 완료되었습니다.');
            location.replace('/articles');
        };
        function fail() {
            alert('등록 실패했습니다.');
            location.replace('/articles');
        };

        httpRequest('POST','/api/articles', body, success, fail)
    });
}


// 쿠키를 가져오는 함수
function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token'),
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}

