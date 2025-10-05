async function getProfile() {
    const token = localStorage.getItem('jwt');
    const res = await fetch('/api/users/me', {
        headers: { 'Authorization': 'Bearer ' + token }
    });
    const text = await res.text();
    document.getElementById('profile').innerText = text;
}
getProfile();
