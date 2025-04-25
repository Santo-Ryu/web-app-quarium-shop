/* eslint-disable no-unused-vars */

export const AuthForm = ({
    typeForm
}) => {

    return(
        <>
        <HelmetProvider>
            <Helmet>
                <title>{typeForm}</title>
            </Helmet>

            <div className="auth-form">
                
            </div>
        </HelmetProvider>
        </>
    )
}