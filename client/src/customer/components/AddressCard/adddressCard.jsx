import React from 'react'

const AddressCard = ({address}) => {
  return (
    <div className=''> 
      <div className='space-y-3 text-left'>
        <p className='font-semibold'>{address?.firstName+" "+address?.lastName}</p>
        <p>{address?.city},{address?.streetAddress},{address?.zipCode}</p>
        <div className='space-y-1'>
          <p className='font-semibold'>Phone Number</p>
          <p>{address?.mobile}</p>
        </div>
      </div>
    </div>
  )
}

export default AddressCard