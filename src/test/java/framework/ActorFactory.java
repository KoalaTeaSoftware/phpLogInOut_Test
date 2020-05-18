package framework;

import framework.actors.*;

public class ActorFactory {

    // lazily instantiated single instance to live for the entire test
    private static Actor myHandle = null;

    public static Actor getActor(Actor.ActorType type) {
        if (myHandle == null) {
            switch (type) {
                case CHROME:
                    myHandle = new ChromeActor();
                    break;
                case FIREFOX:
                    myHandle = new FirefoxActor();
                    break;
                case INTERNET_EXPLORER:
                    myHandle = new IeActor();
                    break;
//                case SAFARI:
//                    break;
                case ANDROID:
                    myHandle = new AndroidActor();
                    break;
//                case IOS:
//                    break;
                case API:
                    myHandle = new RestActor();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }
        return myHandle;
    }
}
