import axios from 'axios';

// let base = 'http://39.105.27.80/help/';

let base = 'http://localhost/help/';

export const requestLogin = params => { return axios.get(`${base}loginAPI`, { params: params }).then(res => res.data); };

export const requestCode = params => { return axios.get(`${base}getCodeAPI`, { params: params }).then(res => res.data); };

export const getRegInfo = params => { return axios.get(`${base}getRegInfoAPI`).then(res => res.data); };

export const getUserInfo = params => { return axios.get(`${base}getUserInfoAPI`).then(res => res.data); };

export const sendRegJihuo = params => { return axios.get(`${base}sendRegEmailAPI`, { params: params }).then(res => res.data); };

export const requestReg = params => { return axios.get(`${base}requestRegAPI`, { params: params }).then(res => res.data); };

export const deleteReginfo = params => { return axios.get(`${base}deleteRegAPI`, { params: params }).then(res => res.data); };

export const removeUser = params => { return axios.get(`${base}/removeUserAPI`, { params: params }).then(res => res.data); };

export const GetTixianInfo = params => { return axios.get(`${base}/getTixianInfoAPI`).then(res => res.data); };

export const ExcuteTixianInfo = params => { return axios.get(`${base}/excuteTixianInfoAPI`, { params: params }).then(res => res.data); };

export const GetTixianMessage = params => { return axios.get(`${base}/gettixianmessageAPI`, { params: params }).then(res => res.data); };

export const userAlreadReadmessage = params => { return axios.get(`${base}/userAlreadReadmessageAPI`, { params: params }).then(res => res.data); };

export const modifyuserPW = params => { return axios.get(`${base}/modifyuserPWAPI`, { params: params }).then(res => res.data); };

export const GetUsrJizhangInfo = params => { return axios.get(`${base}/getUserJizhangInfoAPI`, { params: params }).then(res => res.data); };

export const ModifyUserJizhangInfo = params => { return axios.get(`${base}/ModifyUserJizhangInfoAPI`, { params: params }).then(res => res.data); };

export const DeleteUserJizhangInfo = params => { return axios.get(`${base}/DeleteUserJizhangInfoAPI`, { params: params }).then(res => res.data); };

export const GetSumbangdanRec = params => { return axios.get(`${base}/GetSumbangdanInfoAPI`, { params: params }).then(res => res.data); };

export const GetJizhangSumInfo = params => { return axios.get(`${base}/GetJizhangSumInfoAPI`, { params: params }).then(res => res.data); };

export const AddUserJizhangDayInfo = params => { return axios.get(`${base}/AddUserJizhangDayInfoAPI`, { params: params }).then(res => res.data); };

export const DeleteUserSumJizhang = params => { return axios.get(`${base}/DeleteUserSumJizhangAPI`, { params: params }).then(res => res.data); };

export const SaveUserInfo = params => { return axios.get(`${base}/SaveUserInfoAPI`, { params: params }).then(res => res.data); };

export const AddPLShouyiInfo = params => { return axios.get(`${base}/AddPLShouyiInfoAPI`, { params: params }).then(res => res.data); };

export const GetOptRecInfo = params => { return axios.get(`${base}/GetOptRecInfoAPI`).then(res => res.data); };

export const ClearOptRecInfo = params => { return axios.get(`${base}/ClearOptRecInfoAPI`).then(res => res.data); };

export const GetbackrecInfo = params => { return axios.get(`${base}/GetbackrecInfoAPI`).then(res => res.data); };

export const BackdataInfo = params => { return axios.get(`${base}/BackdataInfoAPI`).then(res => res.data); };

export const RestorDataInfo = params => { return axios.get(`${base}/RestorDataInfoAPI`, { params: params }).then(res => res.data); };
