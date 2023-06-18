import axios from "axios";

export async function login({ username, password }) {
  const res = await axios.post(`/auth/login`, {
    username,
    password,
  });
  return res.data;
}


export async function fetchSelf(token) {
    const res = await axios.post(`/user/account/me`, {}, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });
    return res.data;
}