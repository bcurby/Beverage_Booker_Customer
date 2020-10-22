package com.beveragebooker.customer_app.notifications;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PrivacyDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Privacy Policy")
                .setMessage(
                        "Beverage Booker built the Beverage Booker app as a Free app. This SERVICE is provided by Beverage Booker at no cost and is intended for use as is.\n" +
                        "\n" +
                        "This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decides to use our Service.\n" +
                        "\n" +
                        "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                        "\n" +
                        "Information Collection and Use\n" +
                        "\n" +
                        "We will only collect personal information from you that is necessary for us to be able to meet any orders, provide our services and/or provide any assistance you have requested. We will seek to ensure that we do not collect information from you in any way that is unlawful or unreasonably intrusive.\n" +
                        "\n" +
                        "The personal information that we collect from you and hold (“Information”) to create an account and use our service includes your first and last names, email address and mobile number. Providing this information allows for use of the app.\n" +
                        "\n" +
                        "To purchase any of the cafe's products through the app, payment must be provided via credit card. We do not personally store this information in our database, but this service is provided by Stripe through the Beverage Booker app. Supplying your credit card details and making payment through the app will be subject to Stripe’s Privacy Policy.\n" +
                        "\n" +
                        "If you select the delivery option during an order, you will need to enter your address. This includes a unit number if applicable, and a street number and street name. These details are stored in our Beverage Booker database so the delivery drivers can complete their deliveries. Deliveries are only offered in the City of Bathurst, NSW, Australia, so all valid delivery addresses should relate to this location.\n" +
                        "\n" +
                        "All account and order data is stored in the Beverage Booker database that is hosted through the web hosting company iPage, who are part of the Endurance Group. By signing up on the Beverage Booker app you are also agreeing to have your data stored with iPage hosting, and as such you are agreeing to their privacy terms concerning your personal data. \n" +
                        "As mentioned, the app does use third party services that may collect information used to identify you. These include the following and links are provided to their privacy policies:\n" +
                        "\n" +
                        "Google Play Services\n" +
                        "https://www.google.com/policies/privacy/\n" +
                        "\n" +
                        "Stripe Privacy Policy\n" +
                        "https://stripe.com/en-au/privacy\n" +
                        "\n" +
                        "Our web server and database hosted by iPage (Endurance Group):\n" +
                        "https://www.endurance.com/privacy/privacy\n" +
                        "\n" +
                        "Youtube Terms of Service:\n" +
                        "https://www.youtube.com/static?template=terms&gl=AU\n" +
                        "\n" +
                        "Google Privacy Policy (Youtube Parent Company):\n" +
                        "https://www.google.com/policies/privacy/\n" +
                        "\n" +
                        "Updating Your Data\n" +
                        "\n" +
                        "If a registered user of the app at any time thinks the information held concerning them is not accurate, then their personal details can be edited and updated through the Account page of the app.\n" +
                        "\n" +
                        "If a user no longer wishes to use the app and would like their details removed from our database, the user can also delete their account through the Account page of the app. Doing so removes the users name, email and mobile number from our database. Data relating to orders already placed within the app may persist, but will only be referenced by a user ID number and will not be identifiably linked to a customer who has deleted their account.\n" +
                        "\n" +
                        "Cookies\n" +
                        "\n" +
                        "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
                        "\n" +
                        "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse cookies from these third party providers, you may not be able to use some portions of this Service.\n" +
                        "\n" +
                        "Service Providers\n" +
                        "\n" +
                        "We may employ third-party companies and individuals due to the following reasons:\n" +
                        "\n" +
                        "*   To facilitate our Service;\n" +
                        "*   To provide the Service on our behalf;\n" +
                        "*   To perform Service-related services; or\n" +
                        "*   To assist us in analyzing how our Service is used.\n" +
                        "\n" +
                        "We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.\n" +
                        "\n" +
                        "Security\n" +
                        "\n" +
                        "We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. To help ensure your data remains private, all communication to and from the Beverage Booker app is via encrypted HTTPS. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.\n" +
                        "\n" +
                        "Links to Other Sites\n" +
                        "\n" +
                        "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.\n" +
                        "\n" +
                        "Children’s Privacy\n" +
                        "\n" +
                        "These Services do not address anyone under the age of 13. We do not knowingly collect personally identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions. \n" +
                        "\n" +
                        "Changes to This Privacy Policy\n" +
                        "\n" +
                        "We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page.\n" +
                        "\n" +
                        "This policy is effective as of 29-09-2020\n" +
                        "\n" +
                        "Contact Us\n" +
                        "\n" +
                        "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at beveragebooker@gmail.com.\n" +
                        "\n" +
                        "This privacy policy can also be viewed at the following website:\n" +
                        "\n" +
                        "https://sites.google.com/view/beveragerbookerprivacypolicy/home\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
