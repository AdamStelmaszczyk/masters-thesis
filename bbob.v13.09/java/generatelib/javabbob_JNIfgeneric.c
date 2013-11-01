#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

#include <jni.h>

#include "bbobStructures.h" /* Include all declarations for BBOB calls */
#include "benchmarkshelper.h"

#include "javabbob_JNIfgeneric.h"

/* Source for class javabbob_JNIcon */

/* TODO Error checking! */

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    initBBOB
 * Signature: (IIILjava/lang/String;Ljavabbob/JNIfgeneric/Params;)D
 */
JNIEXPORT jdouble JNICALL Java_javabbob_JNIfgeneric_initBBOB
  (JNIEnv * jenv, jobject obj, jint funcID, jint instanceID, jint dim,
   jstring out_path, jobject inputparams)
{

    double ret = 0.;
    const char *path;
    const char *alg;
    const char *comment;
    const char *fileprefix;
    jstring jalg;
    jstring jcomment;
    jstring jfileprefix;
    jfieldID fid;
    jclass cls;
    /* retrieve all default parameters of BBOB calls  */
    ParamStruct params;

    /* This test is both to prevent warning because obj was not used and
       check exceptions. */
    if (obj == NULL)
        printf("Null obj found.\n");

    /* get attributes from jobject inputparams */
    cls = (*jenv)->GetObjectClass(jenv, inputparams);
    if (cls == NULL)
        printf("Null cls.\n");

    /* Look for the instance field s in cls */
    fid = (*jenv)->GetFieldID(jenv, cls, "algName", "Ljava/lang/String;");
    if(fid == NULL)
        printf("Null fid.\n");
    jalg = (*jenv)->GetObjectField(jenv, inputparams, fid);
    alg = (*jenv)->GetStringUTFChars(jenv, jalg, NULL);

    fid = (*jenv)->GetFieldID(jenv, cls, "comments", "Ljava/lang/String;");
    if(fid == NULL)
        printf("Null fid2.\n");
    jcomment = (*jenv)->GetObjectField(jenv, inputparams, fid);
    comment = (*jenv)->GetStringUTFChars(jenv, jcomment, NULL);

    fid = (*jenv)->GetFieldID(jenv, cls, "filePrefix", "Ljava/lang/String;");
    if(fid == NULL)
        printf("Null fid3.\n");
    jfileprefix = (*jenv)->GetObjectField(jenv, inputparams, fid);
    fileprefix = (*jenv)->GetStringUTFChars(jenv, jfileprefix, NULL);

    /* convert java strings */
    path = (*jenv)->GetStringUTFChars(jenv, out_path, NULL);

    /* retrieve all default parameters of BBOB calls  */
    params = fgeneric_getDefaultPARAMS();

    /* put the java given parameters into bbob parameter struct */
    strcpy(params.dataPath, path);
    /* printf("obtained output path: %s\n", path); */
    strcpy(params.algName, alg);
    strcpy(params.comments, comment);
    strcpy(params.filePrefix, fileprefix);

    params.DIM = dim;
    params.funcId = funcID;
    params.instanceId = instanceID ;

    /* call the BBOB initialization */
    ret = fgeneric_initialize(params);

    /* free ressources */
    (*jenv)->ReleaseStringUTFChars(jenv, jalg, alg);
    (*jenv)->ReleaseStringUTFChars(jenv, jcomment, comment);
    (*jenv)->ReleaseStringUTFChars(jenv, jfileprefix, fileprefix);
    (*jenv)->ReleaseStringUTFChars(jenv, out_path, path );
    /* printf("\nFinished initBBOB\n"); */

    return ret;
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    exitBBOB
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_javabbob_JNIfgeneric_exitBBOB
  (JNIEnv * jenv, jobject obj)
{
    double ret = 0.;
    if (obj == NULL)
        printf("Null obj found.\n");
    if (jenv == NULL)
        printf("Null obj found.\n");

    ret = fgeneric_finalize();

    /* printf("\nFinished exitBBOB\n"); */
    return ret;
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    exist
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_javabbob_JNIfgeneric_exist
  (JNIEnv *jenv, jobject obj, jint funcid)
{
    if (jenv == NULL)
        printf("Null obj found.\n");
    if (obj == NULL)
        printf("Null obj found.\n");

    if (fgeneric_exist(funcid))
        return JNI_TRUE;
    return JNI_FALSE;
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    getFtarget
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_javabbob_JNIfgeneric_getFtarget
  (JNIEnv * jenv, jobject obj)
{
    if (jenv == NULL)
        printf("Null obj found.\n");
    if (obj == NULL)
        printf("Null obj found.\n");

    return fgeneric_ftarget();
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    getBest
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_javabbob_JNIfgeneric_getBest
  (JNIEnv * jenv, jobject obj)
{
    if (jenv == NULL)
        printf("Null obj found.\n");
    if (obj == NULL)
        printf("Null obj found.\n");

    return fgeneric_best();
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    getFbest
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_javabbob_JNIfgeneric_getFbest
  (JNIEnv * jenv, jobject obj)
{
    if (jenv == NULL)
        printf("Null obj found.\n");
    if (obj == NULL)
        printf("Null obj found.\n");

    return fgeneric_best();
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    getEvaluations
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_javabbob_JNIfgeneric_getEvaluations
  (JNIEnv * jenv, jobject obj)
{
    if (jenv == NULL)
        printf("Null obj found.\n");
    if (obj == NULL)
        printf("Null obj found.\n");

    return fgeneric_evaluations();
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    setNoiseSeed
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_javabbob_JNIfgeneric_setNoiseSeed
  (JNIEnv * jenv, jobject obj, jint seed)
{
    if (jenv == NULL)
        printf("Null obj found.\n");
    if (obj == NULL)
        printf("Null obj found.\n");

    fgeneric_noiseseed(seed);
}

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    unif
 * Signature: ([DII)V
 */
JNIEXPORT void JNICALL Java_javabbob_JNIfgeneric_unif
  (JNIEnv * jenv, jobject obj, jdoubleArray r, jint N, jint inseed)
 {
    jdouble *cr = (*jenv)->GetDoubleArrayElements(jenv, r, NULL);

    if (obj == NULL)
        printf("Null obj found.\n");

    unif(cr, N, inseed);
    (*jenv)->ReleaseDoubleArrayElements(jenv, r, cr, 0);
 }

/*
 * Class:     javabbob_JNIfgeneric
 * Method:    evaluate
 * Signature: ([D)D
 */
JNIEXPORT jdouble JNICALL Java_javabbob_JNIfgeneric_evaluate
  (JNIEnv * jenv, jobject obj, jdoubleArray X)
{
    jdouble *cX = (*jenv)->GetDoubleArrayElements(jenv, X, NULL);
    double ret = fgeneric_evaluate(cX);

    if (obj == NULL)
        printf("Null obj found.\n");

    (*jenv)->ReleaseDoubleArrayElements(jenv, X, cX, JNI_ABORT);
    return ret;
}

int main() {
    return 0;
}
