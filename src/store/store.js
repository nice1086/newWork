import { createStore } from 'vuex';


export default createStore({
  state: {
    user: {
        uuid: '',
        captchaImage: ''
      }
  },
  mutations: {
    setUuid(state, uuid) {
      state.user.uuid = uuid
    },
    setCaptchaImage(state, captchaImage) {
      state.user.captchaImage = captchaImage
    }
  },
  actions: {
    // async fetchUuidAndCaptchaImage({ commit }) {
    //   try {
        // 这里编写获取 uuid 和 captchaImage 的异步逻辑，例如通过 API 请求
        // const response = await fetch('/captchaImage');
        // const data = await response.json();
  
        // 将获取到的 uuid 和 captchaImage 提交到 mutations 中进行状态更新
        // commit('setUuid', data.uuid);
        // commit('setCaptchaImage', data.captchaImage);
    //   } catch (error) {
        // console.error('Error fetching uuid and captchaImage:', error);
    //   }
    // },
    
  }
});

