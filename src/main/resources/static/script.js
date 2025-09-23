// 페이지가 로드되면 실행되는 함수
window.onload = function() {
    fetchBoards();
    setupEventListeners();
};

const API_BASE_URL = 'http://localhost:8080/api';
// 페이지가 로드될 때 로컬 스토리지에서 토큰을 불러옵니다.
let token = localStorage.getItem('authToken');
if (token) {
    updateUIForLoggedInUser();
} else {
    updateUIForLoggedOutUser();
}

// DOM 요소들을 가져와 이벤트 리스너를 설정하는 함수
function setupEventListeners() {
    // 버튼
    const showLoginBtn = document.getElementById('show-login');
    const showRegisterBtn = document.getElementById('show-register');
    const logoutBtn = document.getElementById('logout-button');
    const myPostsBtn = document.getElementById('my-posts-button');
    const newPostBtn = document.getElementById('new-post-button');
    const submitPostBtn = document.getElementById('submit-post-button');
    const cancelPostBtn = document.getElementById('cancel-post-button');

    // 폼
    const authFormSection = document.getElementById('auth-form-section');
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const newPostSection = document.getElementById('new-post-section');

    // 이벤트 리스너
    showLoginBtn.addEventListener('click', () => {
        authFormSection.style.display = 'block';
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
        newPostSection.style.display = 'none';
    });

    showRegisterBtn.addEventListener('click', () => {
        authFormSection.style.display = 'block';
        registerForm.style.display = 'block';
        loginForm.style.display = 'none';
        newPostSection.style.display = 'none';
    });

    document.getElementById('hide-login-form').addEventListener('click', () => {
        authFormSection.style.display = 'none';
    });

    document.getElementById('hide-register-form').addEventListener('click', () => {
        authFormSection.style.display = 'none';
    });

    myPostsBtn.addEventListener('click', fetchMyPosts);
    newPostBtn.addEventListener('click', () => {
        document.getElementById('board-list-section').style.display = 'none';
        document.getElementById('new-post-section').style.display = 'block';
    });

    cancelPostBtn.addEventListener('click', () => {
        document.getElementById('new-post-section').style.display = 'none';
        document.getElementById('board-list-section').style.display = 'block';
        fetchBoards();
    });

    submitPostBtn.addEventListener('click', handleSubmitPost);
    document.getElementById('login-button').addEventListener('click', handleLogin);
    document.getElementById('register-button').addEventListener('click', handleRegister);
    logoutBtn.addEventListener('click', handleLogout);
}

// UI 업데이트 함수 (로그인 상태에 따라 버튼 변경)
function updateUIForLoggedInUser() {
    document.getElementById('show-login').style.display = 'none';
    document.getElementById('show-register').style.display = 'none';
    document.getElementById('logout-button').style.display = 'inline';
    document.getElementById('my-posts-button').style.display = 'inline';
    document.getElementById('new-post-button').style.display = 'inline';
}

function updateUIForLoggedOutUser() {
    document.getElementById('show-login').style.display = 'inline';
    document.getElementById('show-register').style.display = 'inline';
    document.getElementById('logout-button').style.display = 'none';
    document.getElementById('my-posts-button').style.display = 'none';
    document.getElementById('new-post-button').style.display = 'none';
}

// 게시물 목록 불러오기 (전체/내 게시물)
async function fetchBoards(myPosts = false) {
    const boardListElement = document.getElementById('board-list');
    boardListElement.innerHTML = '<li>게시물 목록을 불러오는 중입니다...</li>';
    const url = myPosts ? `${API_BASE_URL}/boards/my` : `${API_BASE_URL}/boards`;
    const headers = {
        'Content-Type': 'application/json'
    };
    if (myPosts && token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    try {
        const response = await fetch(url, { headers });
        if (!response.ok) {
            throw new Error('네트워크 응답이 실패했습니다.');
        }

        const boards = await response.json();
        boardListElement.innerHTML = '';
        if (boards.length === 0) {
            boardListElement.innerHTML = `<li>${myPosts ? '작성한 게시물이' : '게시물이'} 없습니다.</li>`;
            return;
        }

        boards.forEach(board => {
            const listItem = document.createElement('li');
            listItem.innerHTML = `
                <h4>${board.name}</h4>
                <p>${board.content.substring(0, 100)}...</p>
                <p><small>작성일: ${new Date(board.createdAt).toLocaleString()}</small></p>
            `;
            boardListElement.appendChild(listItem);
        });

    } catch (error) {
        console.error('게시물 목록을 가져오는 중 오류가 발생했습니다:', error);
        boardListElement.innerHTML = '<li>게시물을 불러오는 중 오류가 발생했습니다.</li>';
    }
}

async function fetchMyPosts() {
    fetchBoards(true);
}

// 회원가입 처리 함수
async function handleRegister() {
    const studentId = document.getElementById('register-student-id').value;
    const password = document.getElementById('register-password').value;
    const name = document.getElementById('register-name').value;
    const major = document.getElementById('register-major').value;
    const school = document.getElementById('register-school').value;

    const data = { studentId, password, name, major, school };

    try {
        const response = await fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            alert('회원가입 성공!');
            document.getElementById('auth-form-section').style.display = 'none';
        } else {
            const errorData = await response.json();
            alert('회원가입 실패: ' + errorData.message);
        }
    } catch (error) {
        console.error('회원가입 중 오류:', error);
        alert('회원가입 중 오류가 발생했습니다.');
    }
}

// 로그인 처리 함수
async function handleLogin() {
    const studentId = document.getElementById('login-student-id').value;
    const password = document.getElementById('login-password').value;

    const data = { studentId, password };

    try {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            const result = await response.json();
            token = result.token; // 토큰을 전역 변수에 저장
            // 로컬 스토리지에 토큰을 저장하여 로그인 상태 유지
            localStorage.setItem('authToken', token);
            alert('로그인 성공!');
            document.getElementById('auth-form-section').style.display = 'none';
            updateUIForLoggedInUser();
            fetchBoards(); // 게시물 목록 새로고침
        } else {
            alert('로그인 실패. 학번 또는 비밀번호를 확인하세요.');
        }
    } catch (error) {
        console.error('로그인 중 오류:', error);
        alert('로그인 중 오류가 발생했습니다.');
    }
}

// 로그아웃 처리 함수
function handleLogout() {
    token = null;
    // 로컬 스토리지에서 토큰을 삭제
    localStorage.removeItem('authToken');
    alert('로그아웃되었습니다.');
    updateUIForLoggedOutUser();
    fetchBoards(); // 전체 게시물 목록으로 돌아옴
}

// 새 게시물 작성 처리 함수
async function handleSubmitPost(event) {
    event.preventDefault();
    const content = document.getElementById('post-content').value;

    if (!content) {
        alert('내용을 입력하세요.');
        return;
    }

    try {
        const headers = {
            'Content-Type': 'application/json'
        };
        // 토큰이 있을 경우에만 Authorization 헤더를 추가
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        if (!token) {
                alert('토큰이 없습니다. 다시 로그인해주세요.');
                return;
            }

        const response = await fetch(`${API_BASE_URL}/boards`, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify({ content: content })
        });

        if (response.ok) {
            alert('게시물이 성공적으로 작성되었습니다.');
            document.getElementById('post-content').value = ''; // 폼 초기화
            document.getElementById('new-post-section').style.display = 'none';
            document.getElementById('board-list-section').style.display = 'block';
            fetchBoards(); // 게시물 목록 새로고침
        } else if (response.status === 403) {
            alert('인증이 필요합니다. 다시 로그인해주세요.');
            handleLogout();
        } else {
            const errorData = await response.json();
            alert('게시물 작성 실패: ' + errorData.message);
        }
    } catch (error) {
        console.error('게시물 작성 중 오류:', error);
        alert('게시물 작성 중 오류가 발생했습니다.');
    }
}