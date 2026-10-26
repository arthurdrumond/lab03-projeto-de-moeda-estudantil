/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        ink: '#16302b',
        mint: '#dff7ee',
        teal: '#0f766e',
      },
      boxShadow: {
        soft: '0 20px 60px rgba(20, 67, 57, 0.10)',
      },
    },
  },
  plugins: [],
}
