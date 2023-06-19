// import NextAuth from 'next-auth';
// import CredentialsProvider from 'next-auth/providers/credentials';

// export default NextAuth({
//   session: {
//     strategy: 'jwt',
//   },
//   callbacks: {
//     async jwt({ token, user }) {
//       if (user?._id) token._id = user._id;
//       if (user?.isAdmin) token.isAdmin = user.isAdmin;
//       return token;
//     },
//     async session({ session, token }) {
//       if (token?._id) session.user._id = token._id;
//       if (token?.isAdmin) session.user.isAdmin = token.isAdmin;
//       return session;
//     },
//   },
//   providers: [
//     CredentialsProvider({
//       async authorize(credentials) {
//         const 

//       },
//     }),
//   ],
// });

import { fetchSelf, login } from '@/service/userServices'
import NextAuth from 'next-auth'
import CredentialsProvider from 'next-auth/providers/credentials'
export default NextAuth({
  providers: [
    CredentialsProvider({
      name: 'Credentials',

      credentials: {
        username: { label: 'Username', type: 'text' },
        password: { label: 'Password', type: 'password' },
      },
      async authorize(credentials, req) {
        const resToken = await login(
          credentials?.username,
          credentials?.password
        )
        console.log('resToken', resToken);
        const userInfo = await fetchSelf(resToken.data.token)
        const user = { ...resToken.data, ...userInfo.data }
        if (user) {
          return user
        } else {
          return null
        }
      },
    }),
  ],

  pages: {
    signIn: '/login',
  },
  callbacks: {
    async jwt({ token, user }) {
      return { ...token, ...user }
    },
    async session({ session, token }) {
      session.user = token
      return session
    },
  },
})