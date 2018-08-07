/**
 * Created by zhengfh on 2018/8/7
 *
 * @author zhengfh
 **/
package xyz.zpath.java.netty.server;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * @author zhengfh
 * @date 2018/8/7
 **/
public class RpcControllerImpl implements RpcController {
    /**
     * Resets the RpcController to its initial state so that it may be reused in
     * a new call.  This can be called from the client side only.  It must not
     * be called while an RPC is in progress.
     */
    @Override
    public void reset() {

    }

    /**
     * After a call has finished, returns true if the call failed.  The possible
     * reasons for failure depend on the RPC implementation.  {@code failed()}
     * most only be called on the client side, and must not be called before a
     * call has finished.
     */
    @Override
    public boolean failed() {
        return false;
    }

    /**
     * If {@code failed()} is {@code true}, returns a human-readable description
     * of the error.
     */
    @Override
    public String errorText() {
        return null;
    }

    /**
     * Advises the RPC system that the caller desires that the RPC call be
     * canceled.  The RPC system may cancel it immediately, may wait awhile and
     * then cancel it, or may not even cancel the call at all.  If the call is
     * canceled, the "done" callback will still be called and the RpcController
     * will indicate that the call failed at that time.
     */
    @Override
    public void startCancel() {

    }

    /**
     * Causes {@code failed()} to return true on the client side.  {@code reason}
     * will be incorporated into the message returned by {@code errorText()}.
     * If you find you need to return machine-readable information about
     * failures, you should incorporate it into your response protocol buffer
     * and should NOT call {@code setFailed()}.
     *
     * @param reason
     */
    @Override
    public void setFailed(String reason) {

    }

    /**
     * If {@code true}, indicates that the client canceled the RPC, so the server
     * may as well give up on replying to it.  This method must be called on the
     * server side only.  The server should still call the final "done" callback.
     */
    @Override
    public boolean isCanceled() {
        return false;
    }

    /**
     * Asks that the given callback be called when the RPC is canceled.  The
     * parameter passed to the callback will always be {@code null}.  The
     * callback will always be called exactly once.  If the RPC completes without
     * being canceled, the callback will be called after completion.  If the RPC
     * has already been canceled when NotifyOnCancel() is called, the callback
     * will be called immediately.
     *
     * <p>{@code notifyOnCancel()} must be called no more than once per request.
     * It must be called on the server side only.
     *
     * @param callback
     */
    @Override
    public void notifyOnCancel(RpcCallback<Object> callback) {

    }
}
