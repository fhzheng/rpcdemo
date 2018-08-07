/**
 * Created by zhengfh on 2018/8/2
 *
 * @author zhengfh
 **/
package xyz.zpath.java.rpc.services;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

import com.google.protobuf.ServiceException;
import xyz.zpath.java.rpc.annotation.RpcService;
import xyz.zpath.java.rpc.protobuf.RpcNode.ClassService;
import xyz.zpath.java.rpc.protobuf.RpcNode.ClassRequest;
import xyz.zpath.java.rpc.protobuf.RpcNode.ClassResult;


/**
 * @author zhengfh
 * @date 2018/8/2
 **/
@RpcService(value = ClassService.class)
public class ClassServiceImpl implements ClassService.BlockingInterface, ClassService.Interface {
    @Override
    public ClassResult getClass(RpcController controller, ClassRequest request) throws ServiceException {
        System.out.println(request);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ClassResult result = ClassResult.newBuilder().setName("三年" + request.getId() + "班").setId(request.getId()).build();
        return result;
    }

    /**
     * <code>rpc getClass(.ClassRequest) returns (.ClassResult);</code>
     *
     * @param controller
     * @param request
     * @param done
     */
    @Override
    public void getClass(RpcController controller, ClassRequest request, RpcCallback<ClassResult> done) {
        System.out.println(request);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ClassResult result = ClassResult.newBuilder().setName("三年" + request.getId() + "班").setId(request.getId()).build();
        done.run(result);
    }

    @Override
    public ClassResult deleteClass(RpcController controller, ClassRequest request) throws ServiceException {
        System.out.println(request);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ClassResult result = ClassResult.newBuilder().setName("三年" + request.getId() + "班").setId(request.getId()).build();
        return result;
    }

    /**
     * <code>rpc deleteClass(.ClassRequest) returns (.ClassResult);</code>
     *
     * @param controller
     * @param request
     * @param done
     */
    @Override
    public void deleteClass(RpcController controller, ClassRequest request, RpcCallback<ClassResult> done) {
        System.out.println(request);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ClassResult result = ClassResult.newBuilder().setName("三年" + request.getId() + "班").setId(request.getId()).build();
        done.run(result);
    }
}
