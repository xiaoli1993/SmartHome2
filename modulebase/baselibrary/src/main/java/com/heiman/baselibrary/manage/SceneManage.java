/**
 * Copyright ©深圳市海曼科技有限公司.
 */
package com.heiman.baselibrary.manage;

import com.heiman.baselibrary.mode.Scene;
import com.heiman.baselibrary.mode.scene.PLBean;
import com.heiman.baselibrary.mode.scene.pLBean.SceneBean;
import com.heiman.baselibrary.mode.scene.pLBean.sceneBean.ExecListBean;
import com.heiman.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author : 张泽晋
 * @Time : 2017/6/7 9:12
 * @Description : 
 * @Modify record :
 */

public class SceneManage {
    private static SceneManage instance;
    private List<Scene> sceneList = new ArrayList<>();

    private SceneManage(){

    }

    public static SceneManage getInstance() {
        if (instance == null) {
            instance = new SceneManage();
        }
        return instance;
    }

    public List<Scene> getSceneList() {
        List<Scene> results = new ArrayList<>();
        results.addAll(sceneList);
        return results;
    }

    /**
     * 查找数据库中所有的场景
     * @return
     */
    public List<Scene> findAllScene() {
        sceneList.clear();
        sceneList.addAll(DataSupport.findAll(Scene.class));

        if (sceneList != null && sceneList.size() > 0) {
            for (int i = 0; i < sceneList.size(); i++) {
                List<PLBean> plBeanList = DataSupport.where("scene_id = ?", sceneList.get(i).getId() + "").find(PLBean.class);

                if (plBeanList != null && plBeanList.size() == 1) {
                    sceneList.get(i).setPL(plBeanList.get(0));
                    List<SceneBean> sceneBeenList = DataSupport.where("plbean_id = ?", plBeanList.get(0).getId() + "").find(SceneBean.class);

                    if (sceneBeenList != null && sceneBeenList.size() == 1) {
                        sceneList.get(i).getPL().setOID(sceneBeenList.get(0));
                        List<ExecListBean> execListBeanList = DataSupport.where("scenebean_id = ?", sceneBeenList.get(0).getId() + "").find(ExecListBean.class);

                        if (execListBeanList != null) {
                            sceneList.get(i).getPL().getOID().setExecList(execListBeanList);
                        }
                    }
                }
            }
        }
        return getSceneList();
    }

    /**
     * 通过Scene在数据库中的id获取场景
     * @param id
     * @return
     */
    public Scene getScene(long id) {
        Scene scene = DataSupport.find(Scene.class, id);
        if (scene != null) {
            List<PLBean> plBeanList = DataSupport.where("scene_id = ?", scene.getId() + "").find(PLBean.class);

            if (plBeanList != null && plBeanList.size() == 1) {
                scene.setPL(plBeanList.get(0));
                List<SceneBean> sceneBeenList = DataSupport.where("plbean_id = ?", plBeanList.get(0).getId() + "").find(SceneBean.class);

                if (sceneBeenList != null && sceneBeenList.size() == 1) {
                    scene.getPL().setOID(sceneBeenList.get(0));
                    List<ExecListBean> execListBeanList = DataSupport.where("scenebean_id = ?", sceneBeenList.get(0).getId() + "").find(ExecListBean.class);

                    if (execListBeanList != null) {
                        scene.getPL().getOID().setExecList(execListBeanList);
                    }
                }
            }
        }
        return scene;
    }

    /**
     * 根据场景sceneId获得场景
     * @param sceneId
     * @return
     */
    public Scene getScene(int sceneId) {
        Scene scene = null;
        List<Scene> scenes = DataSupport.where("id = (select scene_id from plbean where id = (select plbean_id from scenebean where sceneid = ?))", sceneId + "").find(Scene.class);
        if (scenes != null && scenes.size() > 0) {
            scene = getScene(scenes.get(0).getId());
        }
        return scene;
    }

    /**
     * 增加场景
     * @param scene
     * @return
     */
    public boolean addScene(Scene scene) {
        if (scene == null) {
            LogUtil.e("scene is null!");
            return false;
        }
        scene.save();

        if (scene.getPL() != null) {
//            scene.getPL().save();
            modifyPLBean(scene.getPL());
            if (scene.getPL().getOID() != null) {
//                scene.getPL().getOID().save();
                modifySceneBean(scene.getPL().getOID());
                if (scene.getPL().getOID().getExecList() != null && scene.getPL().getOID().getExecList().size() >0) {
//                    DataSupport.saveAll(scene.getPL().getOID().getExecList());
                    for (int i = 0; i < scene.getPL().getOID().getExecList().size(); i ++) {
                        modifyExecListBean(scene.getPL().getOID().getExecList().get(i));
                    }
                }
            }
        }

        if (sceneList != null) {
            sceneList.add(scene);
        }
        return true;
    }

    /**
     * 修改单个ExecListBean若存在则更新不存在则增加
     * @param execListBean
     */
    private void modifyExecListBean(ExecListBean execListBean) {
        if (execListBean == null) {
            LogUtil.e("modify execListBean is null!");
            return;
        }
        List<ExecListBean> execListBeanList = null;
        try {
            execListBeanList = DataSupport.where("id = ?", execListBean.getId() + "").find(ExecListBean.class);
        } catch (Exception e) {
            execListBean.save();
        }

        if (execListBeanList != null && execListBeanList.size() > 0) {
            execListBean.update(execListBean.getId());
        } else {
            execListBean.save();
        }
    }

    /**
     * 修改单个SceneBean存在则更新不存在则更新
     * @param sceneBean
     */
    private void modifySceneBean(SceneBean sceneBean) {
        if (sceneBean == null) {
            LogUtil.e("modify sceneBean is null!");
            return;
        }
        List<SceneBean> sceneBeanList = null;
        try {
            sceneBeanList = DataSupport.where("id = ?", sceneBean.getId() + "").find(SceneBean.class);
        } catch (Exception e) {
            sceneBean.save();
        }

        if (sceneBeanList != null && sceneBeanList.size() > 0) {
            sceneBean.update(sceneBean.getId());
        } else {
            sceneBean.save();
        }
    }

    /**
     * 修改单个PLBean存在则更新不存在则添加
     * @param plBean
     */
    private void modifyPLBean(PLBean plBean) {
        if (plBean == null) {
            LogUtil.e("modify plBean is null!");
            return;
        }
        List<PLBean> plBeanList = null;
        try {
            plBeanList = DataSupport.where("id = ?", plBean.getId() + "").find(PLBean.class);
        } catch (Exception e) {
            plBean.save();
        }

        if (plBeanList != null && plBeanList.size() > 0) {
            plBean.update(plBean.getId());
        } else {
            plBean.save();
        }
    }

    /**
     * 修改单个Scene若存在则更新不存在则增加
     * @param scene
     */
    private void modifyScene(Scene scene) {
        if (scene == null) {
            LogUtil.e("modify scene is null!");
            return;
        }
        List<Scene> sceneList = null;
        try {
            sceneList = DataSupport.where("id = ?", scene.getId() + "").find(Scene.class);
        } catch (Exception e) {
            scene.save();
        }

        if (sceneList != null && sceneList.size() > 0) {
            scene.update(scene.getId());
        } else {
            scene.save();
        }
    }

    /**
     * 更新场景
     * @param scene
     */
    public void updateScene(Scene scene) {
        if (scene == null) {
            LogUtil.e("update scene is null!");
            return;
        }
        modifyScene(scene);

        if (scene.getPL()  == null) {
            return;
        }
        modifyPLBean(scene.getPL());

        if (scene.getPL().getOID() == null) {
            return;
        }
        modifySceneBean(scene.getPL().getOID());

        if (scene.getPL().getOID().getExecList() == null) {
            return;
        }
        List<ExecListBean> execListBeanList = scene.getPL().getOID().getExecList();
        for (int i = 0; i < execListBeanList.size(); i++) {
            modifyExecListBean(execListBeanList.get(i));
        }
    }

    /**
     * 删除场景
     * @param scene
     */
    public void deleteScene(Scene scene) {
        if (scene == null) {
            LogUtil.e("delete scene is null");
            return;
        }

        if (scene.getPL() != null && scene.getPL().getOID() != null && scene.getPL().getOID().getExecList() != null) {
            for (int i = 0; i < scene.getPL().getOID().getExecList().size(); i++) {
                ExecListBean execListBean = scene.getPL().getOID().getExecList().get(i);

                if (execListBean != null) {
                    execListBean.delete();
                }
            }
        }

        if (scene.getPL() != null && scene.getPL().getOID() != null) {
            scene.getPL().getOID().delete();
        }

        if (scene.getPL() != null) {
            scene.getPL().delete();
        }

        scene.delete();
    }
}
