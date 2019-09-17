package org.keycloak.services;

import java.util.Locale;
import java.io.Serializable;
import javax.annotation.Generated;
import org.jboss.logging.DelegatingBasicLogger;
import java.lang.String;
import java.io.IOException;
import org.jboss.logging.Logger;
import java.net.URI;
import java.lang.Exception;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.RuntimeException;
import org.keycloak.events.EventListenerProvider;
import org.jboss.logging.BasicLogger;
import org.keycloak.models.ModelDuplicateException;
import org.keycloak.email.EmailException;
import java.lang.Throwable;
import java.lang.Object;
import javax.naming.NamingException;


import static org.jboss.logging.Logger.Level.ERROR;
import static org.jboss.logging.Logger.Level.INFO;
import static org.jboss.logging.Logger.Level.DEBUG;
import static org.jboss.logging.Logger.Level.FATAL;
import static org.jboss.logging.Logger.Level.WARN;

/**
 * Warning this class consists of generated code.
 */
@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2019-08-27T15:36:03+0200")
public class ServicesLogger_$logger extends DelegatingBasicLogger implements ServicesLogger, BasicLogger, Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = ServicesLogger_$logger.class.getName();
    public ServicesLogger_$logger(final Logger log) {
        super(log);
    }
    private static final Locale LOCALE = Locale.ROOT;
    protected Locale getLoggingLocale() {
        return LOCALE;
    }
    @Override
    public final void loadingFrom(final Object from) {
        super.log.logf(FQCN, INFO, null, loadingFrom$str(), from);
    }
    protected String loadingFrom$str() {
        return "KC-SERVICES0001: Loading config from %s";
    }
    @Override
    public final void migrationFailure(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, migrationFailure$str());
    }
    protected String migrationFailure$str() {
        return "KC-SERVICES0002: Failed to migrate datamodel";
    }
    @Override
    public final void realmExists(final String realmName, final String from) {
        super.log.logf(FQCN, INFO, null, realmExists$str(), realmName, from);
    }
    protected String realmExists$str() {
        return "KC-SERVICES0003: Not importing realm %s from %s.  It already exists.";
    }
    @Override
    public final void importedRealm(final String realmName, final String from) {
        super.log.logf(FQCN, INFO, null, importedRealm$str(), realmName, from);
    }
    protected String importedRealm$str() {
        return "KC-SERVICES0004: Imported realm %s from %s.";
    }
    @Override
    public final void unableToImportRealm(final Throwable t, final String realmName, final String from) {
        super.log.logf(FQCN, WARN, t, unableToImportRealm$str(), realmName, from);
    }
    protected String unableToImportRealm$str() {
        return "KC-SERVICES0005: Unable to import realm %s from %s.";
    }
    @Override
    public final void imprtingUsersFrom(final Object from) {
        super.log.logf(FQCN, INFO, null, imprtingUsersFrom$str(), from);
    }
    protected String imprtingUsersFrom$str() {
        return "KC-SERVICES0006: Importing users from '%s'";
    }
    @Override
    public final void failedToLoadUsers(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, failedToLoadUsers$str());
    }
    protected String failedToLoadUsers$str() {
        return "KC-SERVICES0007: Failed to load 'keycloak-add-user.json'";
    }
    @Override
    public final void addUserFailedRealmNotFound(final String user, final String realm) {
        super.log.logf(FQCN, ERROR, null, addUserFailedRealmNotFound$str(), user, realm);
    }
    protected String addUserFailedRealmNotFound$str() {
        return "KC-SERVICES0008: Failed to add user %s to realm %s: realm not found";
    }
    @Override
    public final void addUserSuccess(final String user, final String realm) {
        super.log.logf(FQCN, INFO, null, addUserSuccess$str(), user, realm);
    }
    protected String addUserSuccess$str() {
        return "KC-SERVICES0009: Added user '%s' to realm '%s'";
    }
    @Override
    public final void addUserFailedUserExists(final String user, final String realm) {
        super.log.logf(FQCN, ERROR, null, addUserFailedUserExists$str(), user, realm);
    }
    protected String addUserFailedUserExists$str() {
        return "KC-SERVICES0010: Failed to add user '%s' to realm '%s': user with username exists";
    }
    @Override
    public final void addUserFailed(final Throwable t, final String user, final String realm) {
        super.log.logf(FQCN, ERROR, t, addUserFailed$str(), user, realm);
    }
    protected String addUserFailed$str() {
        return "KC-SERVICES0011: Failed to add user '%s' to realm '%s'";
    }
    @Override
    public final void failedToDeleteFile(final String fileName) {
        super.log.logf(FQCN, ERROR, null, failedToDeleteFile$str(), fileName);
    }
    protected String failedToDeleteFile$str() {
        return "KC-SERVICES0012: Failed to delete '%s'";
    }
    @Override
    public final void failedAuthentication(final Throwable t) {
        super.log.logf(FQCN, WARN, t, failedAuthentication$str());
    }
    protected String failedAuthentication$str() {
        return "KC-SERVICES0013: Failed authentication";
    }
    @Override
    public final void failedClientAuthentication(final Throwable t) {
        super.log.logf(FQCN, DEBUG, t, failedClientAuthentication$str());
    }
    protected String failedClientAuthentication$str() {
        return "KC-SERVICES0014: Failed client authentication";
    }
    @Override
    public final void errorAuthenticatingClient(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, errorAuthenticatingClient$str());
    }
    protected String errorAuthenticatingClient$str() {
        return "KC-SERVICES0015: Unexpected error when authenticating client";
    }
    @Override
    public final void unknownFlow() {
        super.log.logf(FQCN, ERROR, null, unknownFlow$str());
    }
    protected String unknownFlow$str() {
        return "KC-SERVICES0016: Unknown flow to execute with";
    }
    @Override
    public final void unknownResultStatus() {
        super.log.logf(FQCN, ERROR, null, unknownResultStatus$str());
    }
    protected String unknownResultStatus$str() {
        return "KC-SERVICES0017: Unknown result status";
    }
    @Override
    public final void authMethodFallback(final String clientId, final String expectedClientAuthType) {
        super.log.logf(FQCN, WARN, null, authMethodFallback$str(), clientId, expectedClientAuthType);
    }
    protected String authMethodFallback$str() {
        return "KC-SERVICES0018: Client %s doesn't have have authentication method configured. Fallback to %s";
    }
    @Override
    public final void noDuplicationDetected() {
        super.log.logf(FQCN, WARN, null, noDuplicationDetected$str());
    }
    protected String noDuplicationDetected$str() {
        return "KC-SERVICES0019: No duplication detected.";
    }
    @Override
    public final void resetFlow(final String emailOrUserName) {
        super.log.logf(FQCN, WARN, null, resetFlow$str(), emailOrUserName);
    }
    protected String resetFlow$str() {
        return "KC-SERVICES0020: %s is null. Reset flow and enforce showing reviewProfile page";
    }
    @Override
    public final void confirmBrokerEmailFailed(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, confirmBrokerEmailFailed$str());
    }
    protected String confirmBrokerEmailFailed$str() {
        return "KC-SERVICES0021: Failed to send email to confirm identity broker linking";
    }
    @Override
    public final void keyParamDoesNotMatch() {
        super.log.logf(FQCN, ERROR, null, keyParamDoesNotMatch$str());
    }
    protected String keyParamDoesNotMatch$str() {
        return "KC-SERVICES0022: Key parameter don't match with the expected value from client session";
    }
    @Override
    public final void smtpNotConfigured() {
        super.log.logf(FQCN, WARN, null, smtpNotConfigured$str());
    }
    protected String smtpNotConfigured$str() {
        return "KC-SERVICES0023: Smtp is not configured for the realm. Ignoring email verification authenticator";
    }
    @Override
    public final void modelDuplicateException(final ModelDuplicateException mde) {
        super.log.logf(FQCN, ERROR, mde, modelDuplicateException$str());
    }
    protected String modelDuplicateException$str() {
        return "KC-SERVICES0024: ";
    }
    @Override
    public final void errorValidatingAssertion(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, errorValidatingAssertion$str());
    }
    protected String errorValidatingAssertion$str() {
        return "KC-SERVICES0025: Error when validating client assertion";
    }
    @Override
    public final void failedToSendPwdResetEmail(final EmailException e) {
        super.log.logf(FQCN, ERROR, e, failedToSendPwdResetEmail$str());
    }
    protected String failedToSendPwdResetEmail$str() {
        return "KC-SERVICES0026: Failed to send password reset email";
    }
    @Override
    public final void recaptchaFailed(final Exception e) {
        super.log.logf(FQCN, ERROR, e, recaptchaFailed$str());
    }
    protected String recaptchaFailed$str() {
        return "KC-SERVICES0028: Recaptcha failed";
    }
    @Override
    public final void failedToSendEmail(final Exception e) {
        super.log.logf(FQCN, ERROR, e, failedToSendEmail$str());
    }
    protected String failedToSendEmail$str() {
        return "KC-SERVICES0029: Failed to send email";
    }
    @Override
    public final void fullModelImport(final String strategy) {
        super.log.logf(FQCN, INFO, null, fullModelImport$str(), strategy);
    }
    protected String fullModelImport$str() {
        return "KC-SERVICES0030: Full model import requested. Strategy: %s";
    }
    @Override
    public final void realmImportRequested(final String realmName, final String strategy) {
        super.log.logf(FQCN, INFO, null, realmImportRequested$str(), realmName, strategy);
    }
    protected String realmImportRequested$str() {
        return "KC-SERVICES0031: Import of realm '%s' requested. Strategy: %s";
    }
    @Override
    public final void importSuccess() {
        super.log.logf(FQCN, INFO, null, importSuccess$str());
    }
    protected String importSuccess$str() {
        return "KC-SERVICES0032: Import finished successfully";
    }
    @Override
    public final void fullModelExportRequested() {
        super.log.logf(FQCN, INFO, null, fullModelExportRequested$str());
    }
    protected String fullModelExportRequested$str() {
        return "KC-SERVICES0033: Full model export requested";
    }
    @Override
    public final void realmExportRequested(final String realmName) {
        super.log.logf(FQCN, INFO, null, realmExportRequested$str(), realmName);
    }
    protected String realmExportRequested$str() {
        return "KC-SERVICES0034: Export of realm '%s' requested.";
    }
    @Override
    public final void exportSuccess() {
        super.log.logf(FQCN, INFO, null, exportSuccess$str());
    }
    protected String exportSuccess$str() {
        return "KC-SERVICES0035: Export finished successfully";
    }
    @Override
    public final void overwriteError(final Exception e, final String name) {
        super.log.logf(FQCN, ERROR, e, overwriteError$str(), name);
    }
    protected String overwriteError$str() {
        return "KC-SERVICES0036: Error overwriting %s";
    }
    @Override
    public final void creationError(final Exception e, final String name) {
        super.log.logf(FQCN, ERROR, e, creationError$str(), name);
    }
    protected String creationError$str() {
        return "KC-SERVICES0037: Error creating %s";
    }
    @Override
    public final void roleImportError(final Exception e) {
        super.log.logf(FQCN, ERROR, e, roleImportError$str());
    }
    protected String roleImportError$str() {
        return "KC-SERVICES0038: Error importing roles";
    }
    @Override
    public final void untranslatedProtocol(final String errorName) {
        super.log.logf(FQCN, WARN, null, untranslatedProtocol$str(), errorName);
    }
    protected String untranslatedProtocol$str() {
        return "KC-SERVICES0039: Untranslated protocol Error: %s so we return default SAML error";
    }
    @Override
    public final void usingDeprecatedDirectGrantsOnly() {
        super.log.logf(FQCN, WARN, null, usingDeprecatedDirectGrantsOnly$str());
    }
    protected String usingDeprecatedDirectGrantsOnly$str() {
        return "KC-SERVICES0040: Using deprecated 'directGrantsOnly' configuration in JSON representation. It will be removed in future versions";
    }
    @Override
    public final void invokingDeprecatedEndpoint(final URI requestUri) {
        super.log.logf(FQCN, WARN, null, invokingDeprecatedEndpoint$str(), requestUri);
    }
    protected String invokingDeprecatedEndpoint$str() {
        return "KC-SERVICES0041: Invoking deprecated endpoint %s";
    }
    @Override
    public final void responseModeQueryNotAllowed() {
        super.log.logf(FQCN, ERROR, null, responseModeQueryNotAllowed$str());
    }
    protected String responseModeQueryNotAllowed$str() {
        return "KC-SERVICES0042: Response_mode 'query' not allowed for implicit or hybrid flow";
    }
    @Override
    public final void clientSessionNull() {
        super.log.logf(FQCN, ERROR, null, clientSessionNull$str());
    }
    protected String clientSessionNull$str() {
        return "KC-SERVICES0043: Client session is null";
    }
    @Override
    public final void clientModelNull() {
        super.log.logf(FQCN, ERROR, null, clientModelNull$str());
    }
    protected String clientModelNull$str() {
        return "KC-SERVICES0044: Client model in client session is null";
    }
    @Override
    public final void invalidToken() {
        super.log.logf(FQCN, ERROR, null, invalidToken$str());
    }
    protected String invalidToken$str() {
        return "KC-SERVICES0045: Invalid token. Token verification failed.";
    }
    @Override
    public final void multipleValuesForMapper(final String attrValue, final String mapper) {
        super.log.logf(FQCN, WARN, null, multipleValuesForMapper$str(), attrValue, mapper);
    }
    protected String multipleValuesForMapper$str() {
        return "KC-SERVICES0046: Multiple values found '%s' for protocol mapper '%s' but expected just single value";
    }
    @Override
    public final void spiMayChange(final String factoryId, final String factoryClass, final String spiName) {
        super.log.logf(FQCN, WARN, null, spiMayChange$str(), factoryId, factoryClass, spiName);
    }
    protected String spiMayChange$str() {
        return "KC-SERVICES0047: %s (%s) is implementing the internal SPI %s. This SPI is internal and may change without notice";
    }
    @Override
    public final void exceptionDuringRollback(final RuntimeException e) {
        super.log.logf(FQCN, ERROR, e, exceptionDuringRollback$str());
    }
    protected String exceptionDuringRollback$str() {
        return "KC-SERVICES0048: Exception during rollback";
    }
    @Override
    public final void clientRegistrationException(final String message) {
        super.log.logf(FQCN, ERROR, null, clientRegistrationException$str(), message);
    }
    protected String clientRegistrationException$str() {
        return "KC-SERVICES0049: %s";
    }
    @Override
    public final void initializingAdminRealm(final String adminRealmName) {
        super.log.logf(FQCN, INFO, null, initializingAdminRealm$str(), adminRealmName);
    }
    protected String initializingAdminRealm$str() {
        return "KC-SERVICES0050: Initializing %s realm";
    }
    @Override
    public final void failedToLogoutClient(final Exception e) {
        super.log.logf(FQCN, WARN, e, failedToLogoutClient$str());
    }
    protected String failedToLogoutClient$str() {
        return "KC-SERVICES0051: Failed to logout client, continuing";
    }
    @Override
    public final void failedProcessingType(final Exception e) {
        super.log.logf(FQCN, ERROR, e, failedProcessingType$str());
    }
    protected String failedProcessingType$str() {
        return "KC-SERVICES0052: Failed processing type";
    }
    @Override
    public final void loginFailure(final String user, final String ip) {
        super.log.logf(FQCN, WARN, null, loginFailure$str(), user, ip);
    }
    protected String loginFailure$str() {
        return "KC-SERVICES0053: login failure for user %s from ip %s";
    }
    @Override
    public final void unknownAction(final String action) {
        super.log.logf(FQCN, ERROR, null, unknownAction$str(), action);
    }
    protected String unknownAction$str() {
        return "KC-SERVICES0054: Unknown action: %s";
    }
    @Override
    public final void errorAuthenticating(final Exception e, final String message) {
        super.log.logf(FQCN, ERROR, e, errorAuthenticating$str(), message);
    }
    protected String errorAuthenticating$str() {
        return "KC-SERVICES0055: %s";
    }
    @Override
    public final void errorClosingLDAP(final NamingException ne) {
        super.log.logf(FQCN, WARN, ne, errorClosingLDAP$str());
    }
    protected String errorClosingLDAP$str() {
        return "KC-SERVICES0056: Error when closing LDAP connection";
    }
    @Override
    public final void logoutFailed(final IOException ioe, final String clientId) {
        super.log.logf(FQCN, WARN, ioe, logoutFailed$str(), clientId);
    }
    protected String logoutFailed$str() {
        return "KC-SERVICES0057: Logout for client '%s' failed";
    }
    @Override
    public final void failedToSendRevocation(final IOException ioe) {
        super.log.logf(FQCN, WARN, ioe, failedToSendRevocation$str());
    }
    protected String failedToSendRevocation$str() {
        return "KC-SERVICES0058: Failed to send revocation request";
    }
    @Override
    public final void availabilityTestFailed(final String managementUrl) {
        super.log.logf(FQCN, WARN, null, availabilityTestFailed$str(), managementUrl);
    }
    protected String availabilityTestFailed$str() {
        return "KC-SERVICES0059: Availability test failed for uri '%s'";
    }
    @Override
    public final void roleNotInRealm(final String offlineAccessRole) {
        super.log.logf(FQCN, WARN, null, roleNotInRealm$str(), offlineAccessRole);
    }
    protected String roleNotInRealm$str() {
        return "KC-SERVICES0060: Role '%s' not available in realm";
    }
    @Override
    public final void errorDuringFullUserSync(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, errorDuringFullUserSync$str());
    }
    protected String errorDuringFullUserSync$str() {
        return "KC-SERVICES0061: Error occurred during full sync of users";
    }
    @Override
    public final void errorDuringChangedUserSync(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, errorDuringChangedUserSync$str());
    }
    protected String errorDuringChangedUserSync$str() {
        return "KC-SERVICES0062: Error occurred during sync of changed users";
    }
    @Override
    public final void failedToFormatMessage(final String cause) {
        super.log.logf(FQCN, WARN, null, failedToFormatMessage$str(), cause);
    }
    protected String failedToFormatMessage$str() {
        return "KC-SERVICES0063: Failed to format message due to: %s";
    }
    @Override
    public final void failedToloadMessages(final IOException ioe) {
        super.log.logf(FQCN, WARN, ioe, failedToloadMessages$str());
    }
    protected String failedToloadMessages$str() {
        return "KC-SERVICES0064: Failed to load messages";
    }
    @Override
    public final void failedToUpdatePassword(final Exception e) {
        super.log.logf(FQCN, ERROR, e, failedToUpdatePassword$str());
    }
    protected String failedToUpdatePassword$str() {
        return "KC-SERVICES0065: Failed to update Password";
    }
    @Override
    public final void couldNotFireEvent(final Exception e) {
        super.log.logf(FQCN, ERROR, e, couldNotFireEvent$str());
    }
    protected String couldNotFireEvent$str() {
        return "KC-SERVICES0066: Could not fire event.";
    }
    @Override
    public final void failedToParseRestartLoginCookie(final Exception e) {
        super.log.logf(FQCN, ERROR, e, failedToParseRestartLoginCookie$str());
    }
    protected String failedToParseRestartLoginCookie$str() {
        return "KC-SERVICES0067: failed to parse RestartLoginCookie";
    }
    @Override
    public final void notFoundSerializedCtxInClientSession(final String noteKey) {
        super.log.logf(FQCN, ERROR, null, notFoundSerializedCtxInClientSession$str(), noteKey);
    }
    protected String notFoundSerializedCtxInClientSession$str() {
        return "KC-SERVICES0068: Not found serialized context in clientSession under note '%s'";
    }
    @Override
    public final void flowNotConfigForIDP(final String identityProviderAlias) {
        super.log.logf(FQCN, ERROR, null, flowNotConfigForIDP$str(), identityProviderAlias);
    }
    protected String flowNotConfigForIDP$str() {
        return "KC-SERVICES0069: Flow not configured for identity provider '%s'";
    }
    @Override
    public final void flowNotFoundForIDP(final String flowId, final String identityProviderAlias) {
        super.log.logf(FQCN, ERROR, null, flowNotFoundForIDP$str(), flowId, identityProviderAlias);
    }
    protected String flowNotFoundForIDP$str() {
        return "KC-SERVICES0070: Not found configured flow with ID '%s' for identity provider '%s'";
    }
    @Override
    public final void reqdActionDoesNotMatch() {
        super.log.logf(FQCN, ERROR, null, reqdActionDoesNotMatch$str());
    }
    protected String reqdActionDoesNotMatch$str() {
        return "KC-SERVICES0071: required action doesn't match current required action";
    }
    @Override
    public final void invalidKeyForEmailVerification() {
        super.log.logf(FQCN, ERROR, null, invalidKeyForEmailVerification$str());
    }
    protected String invalidKeyForEmailVerification$str() {
        return "KC-SERVICES0072: Invalid key for email verification";
    }
    @Override
    public final void userSessionNull() {
        super.log.logf(FQCN, ERROR, null, userSessionNull$str());
    }
    protected String userSessionNull$str() {
        return "KC-SERVICES0073: User session was null";
    }
    @Override
    public final void actionProviderNull() {
        super.log.logf(FQCN, ERROR, null, actionProviderNull$str());
    }
    protected String actionProviderNull$str() {
        return "KC-SERVICES0074: Required action provider was null";
    }
    @Override
    public final void failedToGetThemeRequest(final Exception e) {
        super.log.logf(FQCN, WARN, e, failedToGetThemeRequest$str());
    }
    protected String failedToGetThemeRequest$str() {
        return "KC-SERVICES0075: Failed to get theme request";
    }
    @Override
    public final void rejectedNonLocalAttemptToCreateInitialUser(final String remoteAddr) {
        super.log.logf(FQCN, ERROR, null, rejectedNonLocalAttemptToCreateInitialUser$str(), remoteAddr);
    }
    protected String rejectedNonLocalAttemptToCreateInitialUser$str() {
        return "KC-SERVICES0076: Rejected non-local attempt to create initial user from %s";
    }
    @Override
    public final void createdInitialAdminUser(final String userName) {
        super.log.logf(FQCN, INFO, null, createdInitialAdminUser$str(), userName);
    }
    protected String createdInitialAdminUser$str() {
        return "KC-SERVICES0077: Created initial admin user with username %s";
    }
    @Override
    public final void initialUserAlreadyCreated() {
        super.log.logf(FQCN, WARN, null, initialUserAlreadyCreated$str());
    }
    protected String initialUserAlreadyCreated$str() {
        return "KC-SERVICES0078: Rejected attempt to create initial user as user is already created";
    }
    @Override
    public final void localeNotSpecified() {
        super.log.logf(FQCN, WARN, null, localeNotSpecified$str());
    }
    protected String localeNotSpecified$str() {
        return "KC-SERVICES0079: Locale not specified for messages.json";
    }
    @Override
    public final void msgBundleNotFound(final String lang) {
        super.log.logf(FQCN, WARN, null, msgBundleNotFound$str(), lang);
    }
    protected String msgBundleNotFound$str() {
        return "KC-SERVICES0080: Message bundle not found for language code '%s'";
    }
    @Override
    public final void msgBundleNotFoundForEn() {
        super.log.logf(FQCN, FATAL, null, msgBundleNotFoundForEn$str());
    }
    protected String msgBundleNotFoundForEn$str() {
        return "KC-SERVICES0081: Message bundle not found for language code 'en'";
    }
    @Override
    public final void noEventStoreProvider() {
        super.log.logf(FQCN, ERROR, null, noEventStoreProvider$str());
    }
    protected String noEventStoreProvider$str() {
        return "KC-SERVICES0082: Admin Events enabled, but no event store provider configured";
    }
    @Override
    public final void providerNotFound(final String id) {
        super.log.logf(FQCN, ERROR, null, providerNotFound$str(), id);
    }
    protected String providerNotFound$str() {
        return "KC-SERVICES0083: Event listener '%s' registered, but provider not found";
    }
    @Override
    public final void failedToSaveEvent(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, failedToSaveEvent$str());
    }
    protected String failedToSaveEvent$str() {
        return "KC-SERVICES0084: Failed to save event";
    }
    @Override
    public final void failedToSendType(final Throwable t, final EventListenerProvider listener) {
        super.log.logf(FQCN, ERROR, t, failedToSendType$str(), listener);
    }
    protected String failedToSendType$str() {
        return "KC-SERVICES0085: Failed to send type to %s";
    }
    @Override
    public final void addedKerberosToRealmCredentials() {
        super.log.logf(FQCN, INFO, null, addedKerberosToRealmCredentials$str());
    }
    protected String addedKerberosToRealmCredentials$str() {
        return "KC-SERVICES0086: Added 'kerberos' to required realm credentials";
    }
    @Override
    public final void syncingDataForMapper(final String modelName, final String mapperType, final String direction) {
        super.log.logf(FQCN, INFO, null, syncingDataForMapper$str(), modelName, mapperType, direction);
    }
    protected String syncingDataForMapper$str() {
        return "KC-SERVICES0087: Syncing data for mapper '%s' of type '%s'. Direction: %s";
    }
    @Override
    public final void failedToSendActionsEmail(final EmailException e) {
        super.log.logf(FQCN, ERROR, e, failedToSendActionsEmail$str());
    }
    protected String failedToSendActionsEmail$str() {
        return "KC-SERVICES0088: Failed to send execute actions email";
    }
    @Override
    public final void failedToRunScheduledTask(final Throwable t, final String taskClass) {
        super.log.logf(FQCN, ERROR, t, failedToRunScheduledTask$str(), taskClass);
    }
    protected String failedToRunScheduledTask$str() {
        return "KC-SERVICES0089: Failed to run scheduled task %s";
    }
    @Override
    public final void failedToCloseProviderSession(final Throwable t) {
        super.log.logf(FQCN, ERROR, t, failedToCloseProviderSession$str());
    }
    protected String failedToCloseProviderSession$str() {
        return "KC-SERVICES0090: Failed to close ProviderSession";
    }
    @Override
    public final void oidcScopeMissing() {
        if (super.log.isEnabled(WARN) && oidcScopeMissing_$Once.compareAndSet(false, true)) {
            super.log.logf(FQCN, WARN, null, oidcScopeMissing$str());
        }
    }
    protected String oidcScopeMissing$str() {
        return "KC-SERVICES0091: Request is missing scope 'openid' so it's not treated as OIDC, but just pure OAuth2 request.";
    }
    private static final AtomicBoolean oidcScopeMissing_$Once = new AtomicBoolean(false);
    @Override
    public final void missingParameter(final String paramName) {
        super.log.logf(FQCN, ERROR, null, missingParameter$str(), paramName);
    }
    protected String missingParameter$str() {
        return "KC-SERVICES0092: Missing parameter: %s";
    }
    @Override
    public final void invalidParameter(final String paramName) {
        super.log.logf(FQCN, ERROR, null, invalidParameter$str(), paramName);
    }
    protected String invalidParameter$str() {
        return "KC-SERVICES0093: Invalid parameter value for: %s";
    }
    @Override
    public final void unsupportedParameter(final String paramName) {
        super.log.logf(FQCN, ERROR, null, unsupportedParameter$str(), paramName);
    }
    protected String unsupportedParameter$str() {
        return "KC-SERVICES0094: Unsupported parameter: %s";
    }
    @Override
    public final void flowNotAllowed(final String flowName) {
        super.log.logf(FQCN, ERROR, null, flowNotAllowed$str(), flowName);
    }
    protected String flowNotAllowed$str() {
        return "KC-SERVICES0095: Client is not allowed to initiate browser login with given response_type. %s flow is disabled for the client.";
    }
    @Override
    public final void supportedJwkNotFound(final String usage) {
        super.log.logf(FQCN, WARN, null, supportedJwkNotFound$str(), usage);
    }
    protected String supportedJwkNotFound$str() {
        return "KC-SERVICES0096: Not found JWK of supported keyType under jwks_uri for usage: %s";
    }
    @Override
    public final void invalidRequest(final Throwable t) {
        super.log.logf(FQCN, WARN, t, invalidRequest$str());
    }
    protected String invalidRequest$str() {
        return "KC-SERVICES0097: Invalid request";
    }
    @Override
    public final void clientRegistrationRequestRejected(final String opDescription, final String detailedMessage) {
        super.log.logf(FQCN, WARN, null, clientRegistrationRequestRejected$str(), opDescription, detailedMessage);
    }
    protected String clientRegistrationRequestRejected$str() {
        return "KC-SERVICES0099: Operation '%s' rejected. %s";
    }
    @Override
    public final void clientRegistrationMapperNotAllowed(final String mapperName, final String mapperType) {
        super.log.logf(FQCN, WARN, null, clientRegistrationMapperNotAllowed$str(), mapperName, mapperType);
    }
    protected String clientRegistrationMapperNotAllowed$str() {
        return "KC-SERVICES0100: ProtocolMapper '%s' of type '%s' not allowed";
    }
    @Override
    public final void failedToVerifyRemoteHost(final String hostname) {
        super.log.logf(FQCN, WARN, null, failedToVerifyRemoteHost$str(), hostname);
    }
    protected String failedToVerifyRemoteHost$str() {
        return "KC-SERVICES0101: Failed to verify remote host : %s";
    }
    @Override
    public final void urlDoesntMatch(final String url) {
        super.log.logf(FQCN, WARN, null, urlDoesntMatch$str(), url);
    }
    protected String urlDoesntMatch$str() {
        return "KC-SERVICES0102: URL '%s' doesn't match any trustedHost or trustedDomain";
    }
    @Override
    public final void passwordResetFailed(final Throwable t) {
        super.log.logf(FQCN, DEBUG, t, passwordResetFailed$str());
    }
    protected String passwordResetFailed$str() {
        return "KC-SERVICES0103: Failed to reset password. User is temporarily disabled";
    }
    @Override
    public final void notCreatingExistingUser(final String userName) {
        super.log.logf(FQCN, WARN, null, notCreatingExistingUser$str(), userName);
    }
    protected String notCreatingExistingUser$str() {
        return "KC-SERVICES0104: Not creating user %s. It already exists.";
    }
}
