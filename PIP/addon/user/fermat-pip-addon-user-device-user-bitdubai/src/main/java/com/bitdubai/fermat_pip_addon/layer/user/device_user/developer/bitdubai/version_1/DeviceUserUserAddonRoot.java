package com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions.CantGetDeviceUserPersonalImageException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions.CantPersistDeviceUserException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.exceptions.CantPersistDeviceUserPersonalImageFileException;
import com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.structure.DeviceUserUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantCreateNewDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetDeviceUserListException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantSetImageException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.IncorrectUserOrPasswordException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.LoginFailedException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeviceUserCreatedEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeviceUserLoggedInEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeviceUserLoggedOutEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The User Manager knows the users managed by the current device.
 * <p/>
 * It is responsible for login in users to the current device.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on xx/07/2015.
 */

public class DeviceUserUserAddonRoot extends AbstractAddon implements
        DeviceUserManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLATFORM_FILE_SYSTEM)
    private PlatformFileSystem platformFileSystem;


    public DeviceUserUserAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    /**
     * DeviceUserManager Interface member variables.
     */
    DeviceUser mLoggedInDeviceUser;

    final String DEVICE_USER_PUBLIC_KEYS_FILE_NAME = "deviceUserPublicKeys";

    final String PERSONAL_IMAGE_SUFFIX = "_personal_image";

    /**
     * DeviceUserManager Interface implementation.
     */
    // TODO MODIFY WALLET MANAGER MODULE TOO
    // TODO DELETE AFTER CREATING CORRECTLY A DEVICE USER
    String userPublicKey = "a423701d8fcee339376a0c055c9cd80506f71f789b3fc3125b3c7f01c27cc12d";


    @Override
    public DeviceUser getLoggedInDeviceUser() throws CantGetLoggedInDeviceUserException {
        // TODO while we are not creating a user or logging in with one i create one in the moment
        /*if (mLoggedInDeviceUser == null)
            throw new CantGetLoggedInDeviceUserException(CantGetLoggedInDeviceUserException.DEFAULT_MESSAGE, null, "There's no device user logged in.", "");
        return mLoggedInDeviceUser;*/
        try {
            userPublicKey = createNewDeviceUser("test1", "test1");
            DeviceUser deviceUser = getDeviceUser(userPublicKey);
            System.out.println("soy una direccion "+deviceUser.getPublicKey());
            return deviceUser;
        } catch (Exception e) {
            try {
                return getDeviceUser(userPublicKey);
            } catch (Exception t) {
                throw new CantGetLoggedInDeviceUserException(CantGetLoggedInDeviceUserException.DEFAULT_MESSAGE, t, "", "");
            }
        }
    }

    @Override
    public String createNewDeviceUser(String alias, String password) throws CantCreateNewDeviceUserException {
        try {
            ECCKeyPair keyPair = new ECCKeyPair();
            // TODO UNCOMMENT AFTER CREATING CORRECTLY A DEVICE USER
            //DeviceUserUser deviceUser = new DeviceUserUser(alias, password, keyPair.getPrivateKey(), keyPair.getPublicKey());
            DeviceUserUser deviceUser = new DeviceUserUser(alias, password, keyPair.getPrivateKey(), userPublicKey);
            try {
                // TODO UNCOMMENT AFTER CREATING CORRECTLY A DEVICE USER
                //persistNewUserInDeviceUserPublicKeysFile(deviceUser.getPublicKey());
                persistNewUserInDeviceUserPublicKeysFile(userPublicKey);
                persistUser(deviceUser);
            } catch (CantPersistDeviceUserException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Cannot persist the device user information file.", null);
            }

            /**
             * Now I fire the User Created event.
             */
            raiseDeviceUserCreatedEvent(deviceUser.getPublicKey());

            return deviceUser.getPublicKey();
        } catch (Exception e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Unexpected error creating an user.", null);
        }
    }

    @Override
    public String createNewDeviceUser(String alias, String password, byte[] personalImage) throws CantCreateNewDeviceUserException {
        try {
            ECCKeyPair keyPair = new ECCKeyPair();
            try {
                persistUserPersonalImageFile(keyPair.getPrivateKey(), personalImage);
                DeviceUserUser deviceUser = new DeviceUserUser(alias, password, keyPair.getPrivateKey(), keyPair.getPublicKey());
                try {
                    persistNewUserInDeviceUserPublicKeysFile(deviceUser.getPublicKey());
                    persistUser(deviceUser);
                } catch (CantPersistDeviceUserException e) {
                    errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                    throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Cannot persist the device user information file.", null);
                }

                /**
                 * Now I fire the User Created event.
                 */
                raiseDeviceUserCreatedEvent(deviceUser.getPublicKey());

                return deviceUser.getPublicKey();
            } catch (CantPersistDeviceUserPersonalImageFileException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Error trying to persist the device user personal image.", null);
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantCreateNewDeviceUserException(CantCreateNewDeviceUserException.DEFAULT_MESSAGE, e, "Unexpected error creating an user.", null);
        }
    }

    private void persistNewUserInDeviceUserPublicKeysFile(String deviceUserPublicKey) throws CantPersistDeviceUserException {
        try {
            PlatformTextFile file = this.platformFileSystem.createFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    DEVICE_USER_PUBLIC_KEYS_FILE_NAME,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserPublicKeysFile = file.getContent();
            file.setContent((deviceUserPublicKeysFile != null ? deviceUserPublicKeysFile : "")+ deviceUserPublicKey + ";");

        } catch (CantCreateFileException e) {
            throw new CantPersistDeviceUserException(CantPersistDeviceUserException.DEFAULT_MESSAGE, e, "Error getting device user public keys file.", null);
        }
    }

    private void persistUser(DeviceUserUser deviceUser) throws CantPersistDeviceUserException {
        PlatformTextFile file;
        try {
            file = this.platformFileSystem.createFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    deviceUser.getPublicKey(),
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            file.setContent(deviceUser.getAlias() + ";" + deviceUser.getPassword() + ";" + deviceUser.getPrivateKey());

            try {
                file.persistToMedia();
            } catch (CantPersistFileException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantPersistDeviceUserException(CantPersistDeviceUserException.DEFAULT_MESSAGE, e, "Error persisting file.", null);
            }
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantPersistDeviceUserException(CantPersistDeviceUserException.DEFAULT_MESSAGE, e, "Error creating file.", null);
        }
    }

    private void persistUserPersonalImageFile(String publicKey, byte[] personalImage) throws CantPersistDeviceUserPersonalImageFileException {
        try {
            PlatformBinaryFile file = this.platformFileSystem.createBinaryFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey + PERSONAL_IMAGE_SUFFIX,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            file.setContent(personalImage);

            try {
                file.persistToMedia();
            } catch (CantPersistFileException e) {
                throw new CantPersistDeviceUserPersonalImageFileException(CantPersistDeviceUserPersonalImageFileException.DEFAULT_MESSAGE, e, "Error persisting device user personal image file.", null);
            }
        } catch (CantCreateFileException e) {
            throw new CantPersistDeviceUserPersonalImageFileException(CantPersistDeviceUserPersonalImageFileException.DEFAULT_MESSAGE, e, "Error creating device user personal image file.", null);
        }
    }

    private void raiseDeviceUserCreatedEvent(String publicKey) {
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.DEVICE_USER_CREATED);
        ((DeviceUserCreatedEvent) fermatEvent).setPublicKey(publicKey);
        fermatEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
        eventManager.raiseEvent(fermatEvent);
    }

    @Override
    public List<DeviceUser> getAllDeviceUsers() throws CantGetDeviceUserListException {
        try {
            PlatformTextFile file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    DEVICE_USER_PUBLIC_KEYS_FILE_NAME,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserPublicKeysFile = file.getContent();
            String[] deviceUserPublicKeysFileSplit = deviceUserPublicKeysFile.split(";");

            List<DeviceUser> deviceUserList = new ArrayList<>();

            try {
                for (String deviceUserPublicKey : deviceUserPublicKeysFileSplit) {
                    deviceUserList.add(getDeviceUser(deviceUserPublicKey));
                }
            } catch (CantGetDeviceUserException e) {
                errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
                throw new CantGetDeviceUserListException(CantGetDeviceUserListException.DEFAULT_MESSAGE, e, "Error getting device user information file.", null);
            }

            return deviceUserList;

        } catch (FileNotFoundException|CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantGetDeviceUserListException(CantGetDeviceUserListException.DEFAULT_MESSAGE, e, "Error getting device user public keys file.", null);
        }
    }

    @Override
    public DeviceUser getDeviceUser(String publicKey) throws CantGetDeviceUserException {
        PlatformTextFile file;
        try {
            file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserString = file.getContent();
            String[] deviceUserStringSplit = deviceUserString.split(";");
            byte[] personalImage = null;
            try {
                personalImage = getDeviceUserPersonalImage(publicKey);
            } catch (CantGetDeviceUserPersonalImageException e) {
                // TODO QUE HAGO SI NO CONSIGO IMAGEN?
            }
            return new DeviceUserUser(deviceUserStringSplit[0], deviceUserStringSplit[1], deviceUserStringSplit[2], publicKey, personalImage);

        } catch (FileNotFoundException|CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new CantGetDeviceUserException(CantGetDeviceUserException.DEFAULT_MESSAGE, e, "Error getting device user information file.", null);
        }
    }

    private byte[] getDeviceUserPersonalImage(String publicKey) throws CantGetDeviceUserPersonalImageException {
        PlatformBinaryFile file;
        try {
            file = this.platformFileSystem.getBinaryFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey + PERSONAL_IMAGE_SUFFIX,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            return file.getContent();
        } catch (FileNotFoundException|CantCreateFileException e) {
            throw new CantGetDeviceUserPersonalImageException(CantGetDeviceUserPersonalImageException.DEFAULT_MESSAGE, e, "Error trying to get device user personal image file.", null);
        }
    }

    @Override
    public void login(String publicKey, String password) throws LoginFailedException, IncorrectUserOrPasswordException {
        try {
            PlatformTextFile file = this.platformFileSystem.getFile(
                    DeviceDirectory.LOCAL_USERS.getName(),
                    publicKey,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );
            String deviceUserString = file.getContent();
            String[] deviceUserStringSplit = deviceUserString.split(";");

            if (deviceUserStringSplit[1].equals(password)) {
                byte[] personalImage = null;
                try {
                    personalImage = getDeviceUserPersonalImage(publicKey);
                } catch (CantGetDeviceUserPersonalImageException e) {
                    // TODO QUE HAGO SI NO CONSIGO IMAGEN?
                }
                mLoggedInDeviceUser = new DeviceUserUser(deviceUserStringSplit[0], deviceUserStringSplit[1], deviceUserStringSplit[2], publicKey, personalImage);

                /**
                 * If all goes ok i send the event of User logged in
                 */
                FermatEvent fermatEvent = eventManager.getNewEvent(EventType.DEVICE_USER_LOGGED_IN);
                ((DeviceUserLoggedInEvent) fermatEvent).setPublicKey(publicKey);
                fermatEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
                eventManager.raiseEvent(fermatEvent);
            } else {
                throw new IncorrectUserOrPasswordException(IncorrectUserOrPasswordException.DEFAULT_MESSAGE, null, "Bad credentials", "");
            }
        } catch (FileNotFoundException|CantCreateFileException e) {
            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_USER, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, e);
            throw new LoginFailedException(LoginFailedException.DEFAULT_MESSAGE, e, "Error trying to login.", null);
        }
    }

    @Override
    public void logout() {
        /**
         * Raise event user logged out to platform.
         */
        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.DEVICE_USER_LOGGED_OUT);
        ((DeviceUserLoggedOutEvent) fermatEvent).setPublicKey(mLoggedInDeviceUser.getPublicKey());
        fermatEvent.setSource(EventSource.USER_DEVICE_USER_PLUGIN);
        eventManager.raiseEvent(fermatEvent);

        /**
         * Set loggedin User null
         */
        mLoggedInDeviceUser = null;
    }

    @Override
    public void setPersonalImage(byte[] personalImage) throws CantSetImageException {
        try {
            persistUserPersonalImageFile(mLoggedInDeviceUser.getPublicKey(), personalImage);
        } catch(CantPersistDeviceUserPersonalImageFileException e) {
            throw new CantSetImageException(CantSetImageException.DEFAULT_MESSAGE, e, "Cant persist device user personal image.", "");
        }
    }
}
