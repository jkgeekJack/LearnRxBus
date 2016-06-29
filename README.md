# LearnRxBus
RxBus的简易使用
RxBus听名字就知道与RxJava和EventBus有关啦
没错这其实是他们的结合体
其实写一个简单版的也真心不难，RxBus的代码只有**十几行**，没听错真的只有十几行
不过首先你要了解RxJava和EventBus的大概用法，RxJava不用多说，至于EventBus的话他的思想主要就是你在某个地方发送一个Event,然后在另一个地方接收这个Event，仅此而已。
EventBus在跨组件传递数据和解耦方面有得天独厚的优势，这些你接触多久知道了。

现在开讲

##**1.首先导入rxandroid的包**

```
compile 'io.reactivex:rxandroid:1.0.1'
```
##**2.开始写我们的核心RxBus**

```
public class RxBus {
    private static RxBus rxBus=new RxBus();
    public static RxBus getRxBusInstance(){
        return rxBus;
    }
    private final Subject bus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 提供了一个新的事件
    public void post (Object o) {
        bus.onNext(o);
    }
    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObserverable (Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
```
##**3.发送一个Event**

```
RxBus.getRxBusInstance().post(new TapEvent(i));
```
##**4.接收Event**

```
 //subscription是类型为Subscription全局，通常在onDestroy里unsubscribe
        subscription=RxBus.getRxBusInstance().toObserverable(TapEvent.class)
                .subscribe(new Subscriber<TapEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TapEvent tapEvent) {
                        int n=tapEvent.n;
                        tvTap.setText(n+"");
                    }
                });
```

##**5.取消订阅**
如果不想在某个地方继续收到Event可以unsubscribe掉那个subscription
通常放在onDestory里

```
@Override
    public void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
```

我还进行了Activity和service的通讯
##**效果如下**
![这里写图片描述](http://img.blog.csdn.net/20160629194648839)


