import axios from 'axios';
import Cookies from 'js-cookie';

export async function InformationCart({cart}) {
  console.log('cart', cart);
  const res = await axios.post(`/user/cart/information-cart`,{}, {
    headers: {
      Cookie: `cart=${cart}`,
    }
  }
);
  console.log("res: ", res);
  return res.data;
}