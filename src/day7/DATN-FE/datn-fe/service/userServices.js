import axios from "axios";
import { headers } from "next/dist/client/components/headers";
import { URLSearchParams } from "next/dist/compiled/@edge-runtime/primitives/url";

export async function login( username, password ) {
  console.log(username, password);
  const res = await axios.post(`/auth/login`, 
    new URLSearchParams({ username, password }),
    {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }
  );
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